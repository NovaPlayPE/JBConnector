package net.novaplay.jbconnector.spigot.client;

import java.util.*;

import org.bukkit.Bukkit;

public class ClientManager {

	private HashMap<String, Client> clients = new HashMap<String,Client>();
	
	public ClientManager() {}
	
	public boolean addClient(Client client) {
		String serverId = client.getServerId();
		if(!clients.containsKey(serverId.toLowerCase())) {
			clients.put(serverId, client);
			Bukkit.getServer().getLogger().info("[JBConnector] Client '" + serverId+ "' ("+client.getAddress()+":"+client.getPort()+") connected to proxy");
			return true;
		}
		Bukkit.getServer().getLogger().info("[JBConnector] Couldn't connect to proxy. Please, Check data again");
		return false;
	}
	
}
