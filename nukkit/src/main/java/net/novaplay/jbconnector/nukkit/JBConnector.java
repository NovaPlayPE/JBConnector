package net.novaplay.jbconnector.nukkit;

import java.io.File;
import java.io.IOException;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class JBConnector extends PluginBase {
	
	private Config config = null;
	
	public void onEnable() {
		setupConfig();
	}
	
	public Config getPluginConfig() {
		return this.config;
	}
	
	private void setupConfig() {
		File file = new File(getDataFolder() + "/config.yml");
		if(!file.exists()) {
			config = new Config(getDataFolder()+"/config.yml",Config.YAML);
			config.save();
		} else {
			config = new Config(getDataFolder()+"/config.yml",Config.YAML);
		}
	}
	
	private void createConnection() {
		
	}

}
