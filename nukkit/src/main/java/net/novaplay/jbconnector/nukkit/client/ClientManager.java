package net.novaplay.jbconnector.nukkit.client;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Server;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.jbconnector.nukkit.client.Client;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.server.ServerInfoPacket;
import net.novaplay.networking.server.ServerListSyncPacket;

public class ClientManager {

	private HashMap<String, Client> clients = new HashMap<String,Client>();
	public ClientManager() {}
	
	public boolean addClient(Client client) {
		String serverId = client.getServerId();
		if(!clients.containsKey(serverId)) {
			clients.put(serverId, client);
			Server.getInstance().getLogger().info("[JBConnector] Client '" + serverId+ "' ("+client.getAddress()+":"+client.getPort()+") synced with proxy");
			return true;
		}
		Server.getInstance().getLogger().info("[JBConnector] Couldn't sync with proxy. Please, Check data again");
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
	
	public ArrayList<String> getAllClientList(){
		ServerListSyncPacket pk = new ServerListSyncPacket();
		final ArrayList<String> list = new ArrayList<String>();
		JBConnector.getInstance().getSessionManager().sendPacketWithResponse(pk, new Callback() {
			public void accept(Object... o) {
				ServerListSyncPacket p = (ServerListSyncPacket)o[0];
				for(String s : p.serverList) {
					list.add(s);
				}
			}
		});
		return list;
	}
	
	public ArrayList<Client> getClients(){
		ArrayList<Client> c = new ArrayList<Client>();
		for(String s : getAllClientList()) {
			c.add(getClientByName(s));
			return c;
		}
		return null;
	}
	
	public void removeClient(String identifier) {
		
	}
	
	public void reset() {
	}
	
}