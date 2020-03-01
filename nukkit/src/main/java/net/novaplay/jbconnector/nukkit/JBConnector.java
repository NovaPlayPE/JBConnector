package net.novaplay.jbconnector.nukkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.Config;
import net.novaplay.jbconnector.nukkit.client.Client;
import net.novaplay.jbconnector.nukkit.client.ClientManager;
import net.novaplay.jbconnector.nukkit.listener.PacketListener;
import net.novaplay.jbconnector.nukkit.listener.PlayerListener;
import net.novaplay.jbconnector.nukkit.player.JBPlayerManager;
import net.novaplay.jbconnector.nukkit.session.SessionManager;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.server.ProxyConnectPacket;
import net.novaplay.networking.server.ServerListSyncPacket;

public class JBConnector extends PluginBase {

	private Config config = null;
	private Client client = null;
	private boolean connectedStatus = false;
	private SessionManager mgr = null;
	private ClientManager clMgr = null;
	private JBPlayerManager pMgr = null;
	private PacketListener pcLst = null;

	public static JBConnector instance;

	public void onEnable() {
		instance = this;
		pcLst = new PacketListener(this);
		setupConfig();
		
		createConnection();
		getServer().getPluginManager().registerEvents(new PlayerListener(),this);
	}

	public Config getPluginConfig() {
		return this.config;
	}

	private void setupConfig() {
		File file = new File(getDataFolder() + "/config.yml");
		if (!file.exists()) {
			config = new Config(getDataFolder() + "/config.yml", Config.YAML);
			config.set("proxy.address", "0.0.0.0");
			config.set("proxy.port", 9855);
			config.set("proxy.clientId", "Server1");
			config.set("proxy.password", "ExamplePassword123");
			config.save();
		} else {
			config = new Config(getDataFolder() + "/config.yml", Config.YAML);
		}
	}

	public SessionManager getSessionManager() {
		return this.mgr;
	}
	
	public ClientManager getClientManager() {
		return this.clMgr;
	}
	
	public JBPlayerManager getPlayerManager() {
		return this.pMgr ;
	}
	
	public PacketListener getPacketListener() {
		return this.pcLst;
	}

	private void createConnection() {
		String ip = config.getString("proxy.address");
		int port = config.getInt("proxy.port");
		String id = config.getString("proxy.clientId");
		String pass = config.getString("proxy.password");
		
		pMgr = new JBPlayerManager();
		clMgr = new ClientManager();
		
		mgr = new SessionManager(this, ip, port, pass, id);
		mgr.startConnection();

		new NukkitRunnable() {
			@Override
			public void run() {
				if (!connectedStatus) {
					ProxyConnectPacket packet = new ProxyConnectPacket();
					packet.address = getServer().getIp();
					packet.port = getServer().getPort();
					packet.password = pass;
					packet.serverId = id;
					getLogger().info("Spam");
					mgr.sendPacket(packet);
				}
			}
		}.runTaskTimer(this, 0, 20);
	}

	public static JBConnector getInstance() {
		return instance;
	}
	
	public boolean isConnected() {
		return connectedStatus;
	}
	
	public void setConnectedStatus(boolean value) {
		connectedStatus = value;
	}
	
	public String getServerId() {
		return config.getString("proxy.clientId");
	}
	
	public boolean onCommand(Command cmd, CommandSender sender, String label, String[] args) {
		Player player = (Player)sender;
		switch(cmd.getName()) {
		case "transferplayer":
			(JBPlayerManager.newPlayer(args[0])).transfer(args[1]);
			break;
		case "kickplayer":
			(JBPlayerManager.newPlayer(args[0])).kick(args[1]);
			break;
		case "getplayers":
			if(args[0].equals("one")) {
				for(String clients : ClientManager.getAllClientList()) {
					player.sendMessage(clients);
				}
			} else if(args[0].equals("two")) {
				ServerListSyncPacket pk = new ServerListSyncPacket();
				pk.serverList = new ArrayList<String>();
				JBConnector.getInstance().getSessionManager().sendPacketWithResponse(pk, new Callback() {
					public void accept(Object... o) {
						ServerListSyncPacket p = (ServerListSyncPacket)o[0];
						for(String servers : p.serverList) {
							player.sendMessage(servers);
						}
					}
				});
			}
			
			break;	
		}
		return false;
	}

}