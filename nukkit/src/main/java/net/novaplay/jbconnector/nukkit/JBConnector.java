package net.novaplay.jbconnector.nukkit;

import java.io.File;
import java.io.IOException;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.Config;
import net.novaplay.jbconnector.nukkit.client.Client;
import net.novaplay.jbconnector.nukkit.client.ClientManager;
import net.novaplay.jbconnector.nukkit.listener.PlayerListener;
import net.novaplay.jbconnector.nukkit.player.JBPlayerManager;
import net.novaplay.jbconnector.nukkit.session.SessionManager;
import net.novaplay.networking.server.ProxyConnectPacket;
import net.novaplay.networking.types.ConnectType;

public class JBConnector extends PluginBase {

	private Config config = null;
	private Client client = null;
	private boolean connectedStatus = false;
	private SessionManager mgr = null;
	private ClientManager clMgr = null;
	private JBPlayerManager pMgr = null;

	public static JBConnector instance;

	public void onEnable() {
		instance = this;
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
			config.set("proxy.clientId", "bedrock-1");
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
	
	public JBPlayerManager getPLayerManager() {
		return this.pMgr ;
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
					packet.type = ConnectType.JAVA;
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

}