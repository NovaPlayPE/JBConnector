package net.novaplay.jbconnector.spigot;

import org.bukkit.plugin.java.JavaPlugin;

import net.novaplay.jbconnector.spigot.client.Client;
import net.novaplay.jbconnector.spigot.listener.PacketListener;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;

public class JBConnector extends JavaPlugin{
	
	public static JBConnector instance;
	
	private FileConfiguration config = null;
	private Client client = null;
	private PacketListener listener;
	
	public static JBConnector getInstance() {
		return instance;
	}
	
	public void onEnable() {
		instance = this;
		setupConfig();
		getServer().getPluginManager().registerEvents(listener = new PacketListener(this),this);
		createConnection();
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
			config.addDefault("proxy.port", "9855");
			config.addDefault("proxy.clientId", "java-1");
			config.addDefault("proxy.password", "ExamplePassword123");
			config.save(file);
		} catch(IOException | InvalidConfigurationException e) {
			
		}
		
	}
	
	private void createConnection() {
		
	}
	
}