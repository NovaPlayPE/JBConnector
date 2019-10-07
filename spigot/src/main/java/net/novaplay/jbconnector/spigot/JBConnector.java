package net.novaplay.jbconnector.spigot;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.novaplay.jbconnector.spigot.client.Client;
import net.novaplay.jbconnector.spigot.listener.PacketListener;
import net.novaplay.jbconnector.spigot.session.SessionManager;
import net.novaplay.networking.ProxyConnectPacket;
import net.novaplay.networking.types.ConnectType;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;

public class JBConnector extends JavaPlugin{
	
	public static JBConnector instance;
	
	private SessionManager mgr = null;
	private FileConfiguration config = null;
	private Client client = null;
	private PacketListener listener;
	private boolean connectedStatus = false;
	
	public static JBConnector getInstance() {
		return instance;
	}
	
	public void onEnable() {
		instance = this;
		setupConfig();
		getServer().getPluginManager().registerEvents(listener = new PacketListener(this),this);
		createConnection();
	}
	
	public SessionManager getSessionManager() {
		return this.mgr;
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
					packet.address = ip;
					packet.port = port;
					packet.password = pass;
					packet.serverId = id;
					packet.type = ConnectType.JAVA;
					mgr.sendPacket(packet);
				}
			}
		}.runTaskTimer(this,0,20);
	}
	
}