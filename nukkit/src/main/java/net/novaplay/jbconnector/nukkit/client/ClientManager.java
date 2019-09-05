package net.novaplay.jbconnector.nukkit.client;

import java.util.HashMap;

import cn.nukkit.Server;

import net.novaplay.jbconnector.nukkit.client.Client;

public class ClientManager {

	private HashMap<String, Client> clients = new HashMap<String,Client>();
	
	public ClientManager() {}
	
	public boolean addClient(Client client) {
		String serverId = client.getServerId();
		if(!clients.containsKey(serverId.toLowerCase())) {
			clients.put(serverId, client);
			Server.getInstance().getLogger().info("[JBConnector] Client '" + serverId+ "' ("+client.getAddress()+":"+client.getPort()+") connected to proxy");
			return true;
		}
		Server.getInstance().getLogger().info("[JBConnector] Couldn't connect to proxy. Please, Check data again");
		return false;
	}
	
}