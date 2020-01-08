package net.novaplay.jbconnector.spigot.client;

import java.util.*;

import org.bukkit.Bukkit;

import net.novaplay.jbconnector.spigot.JBConnector;
import net.novaplay.jbconnector.spigot.client.Client;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.server.ServerInfoPacket;

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
	
	public Client getClientByName(String id) {
		final ArrayList<Client> crack = new ArrayList<Client>();
		ServerInfoPacket pk = new ServerInfoPacket();
		pk.serverId = id;
		JBConnector.getInstance().getSessionManager().sendPacketWithResponse(pk, new Callback() {
			public void accept(Object... o) {
				ServerInfoPacket p = (ServerInfoPacket)o[0];
				Client c = new Client(p.serverId, p.address, p.port);
				c.setPlayers(p.players);
				crack.add(c);
			}
		});
		return crack.get(0);
	}
	
	public void removeClient(String identifier) {
		
	}
	
	public void reset() {
	}
	
}
