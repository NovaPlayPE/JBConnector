package net.novaplay.jbconnector.nukkit;

import java.io.File;
import java.io.IOException;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import net.novaplay.jbconnector.nukkit.client.Client;

public class JBConnector extends PluginBase {
	
	private Config config = null;
	private Client client = null;
	
	public static JBConnector instance;
	
	public void onEnable() {
		instance = this;
		setupConfig();
		//getServer().getPluginManager().registerEvents(,this);
		createConnection();
	}
	
	public Config getPluginConfig() {
		return this.config;
	}
	
	private void setupConfig() {
		File file = new File(getDataFolder() + "/config.yml");
		if(!file.exists()) {
			config = new Config(getDataFolder()+"/config.yml",Config.YAML);
			config.set("proxy.address", "0.0.0.0");
			config.set("proxy.port", "9855");
			config.set("proxy.clientId", "bedrock-1");
			config.set("proxy.password", "ExamplePassword123");
			config.save();
		} else {
			config = new Config(getDataFolder()+"/config.yml",Config.YAML);
		}
	}
	
	private void createConnection() {
		
	}

	public static JBConnector getInstance() {
		return instance;
	}
	
}