package net.novaplay.jbconnector.spigot;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;

public class JBConnector extends JavaPlugin{
	
	private FileConfiguration config = null;
	
	public void onEnable() {
		setupConfig();
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
			config.addDefault("proxy.type", "java");
			config.addDefault("proxy.password", "ExamplePassword123");
			config.save(file);
		} catch(IOException | InvalidConfigurationException e) {
			
		}
	}
	
	private void createConnection() {
		
	}

}
