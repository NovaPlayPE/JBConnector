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
		} catch(IOException | InvalidConfigurationException e) {
			
		}
	}
	
	private void createConnection() {
		
	}

}
