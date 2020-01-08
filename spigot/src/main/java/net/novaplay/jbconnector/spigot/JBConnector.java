package net.novaplay.jbconnector.spigot;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.novaplay.jbconnector.spigot.client.ClientManager;
import net.novaplay.jbconnector.spigot.listener.PlayerListener;
import net.novaplay.jbconnector.spigot.player.JBPlayerManager;
import net.novaplay.jbconnector.spigot.client.Client;
import net.novaplay.jbconnector.spigot.listener.PacketListener;
import net.novaplay.jbconnector.spigot.session.SessionManager;
import net.novaplay.networking.server.ProxyConnectPacket;
import net.novaplay.networking.types.ConnectType;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;

public class JBConnector extends JavaPlugin{
	
	public static JBConnector instance;
	
	private SessionManager mgr = null;
	private ClientManager clMgr = null;
	private JBPlayerManager pMgr = null;
	
	private FileConfiguration config = null;
	private Client client = null;
	private boolean connectedStatus = false;
	
	public static JBConnector getInstance() {
		return instance;
	}
	
	public void onEnable() {
		instance = this;
		setupConfig();
		createConnection();
		getServer().getPluginManager().registerEvents(new PlayerListener(),this);
	}
	
	public SessionManager getSessionManager() {
		return this.mgr;
	}
	public ClientManager getClientManager() {
		return this.clMgr;
	}
	
	public JBPlayerManager getPlayerManager() {
		return this.pMgr;
	}
	
	
	public FileConfiguration getPluginConfig() {
		return config;
	}
	
	private void setupConfig() {
		File file = new File(getDataFolder(), "config.yml");
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			saveResource("config.yml",false);
		}
		config = new YamlConfiguration();
		try {
			config.load(file);
			config.addDefault("proxy.address", "0.0.0.0");
			config.addDefault("proxy.port", 9855);
			config.addDefault("proxy.clientId", "java-1");
			config.addDefault("proxy.password", "ExamplePassword123");
			config.save(file);
		} catch(IOException | InvalidConfigurationException e) {
			
		}
		
	}
	
	private void createConnection() {
		String ip = config.getString("proxy.address");
		int port = config.getInt("proxy.port");
		String id = config.getString("proxy.clientId");
		String pass = config.getString("proxy.password");
		
		mgr = new SessionManager(this,ip,port,pass,id);
		mgr.startConnection();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(!connectedStatus) {
					ProxyConnectPacket packet = new ProxyConnectPacket();
					packet.address = getServer().getIp();
					packet.port = getServer().getPort();
					packet.password = pass;
					packet.serverId = id;
					packet.type = ConnectType.JAVA;
					mgr.sendPacket(packet);
				}
			}
		}.runTaskTimer(this,0,20);
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