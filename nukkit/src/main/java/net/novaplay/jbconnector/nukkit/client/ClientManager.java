package net.novaplay.jbconnector.nukkit.client;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Server;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.jbconnector.nukkit.client.Client;
import net.novaplay.jbconnector.nukkit.response.GetOnlineClientsResponse;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.server.ServerInfoPacket;
import net.novaplay.networking.server.ServerListSyncPacket;

public class ClientManager {

	private HashMap<String, Client> clients = new HashMap<String,Client>();
	
	private static ArrayList<String> serverList = new ArrayList<String>();
	
	public ClientManager() {}
	
	public boolean addClient(Client client) {
		String serverId = client.getServerId();
		if(!clients.containsKey(serverId)) {
			clients.put(serverId, client);
			sync();
			Server.getInstance().getLogger().info("[BCConnector] Client '" + serverId+ "' ("+client.getAddress()+":"+client.getPort()+") synced with proxy");
			return true;
		}
		Server.getInstance().getLogger().info("[BCConnector] Couldn't sync with proxy. Please, Check data again");
		return false;
	}
	
	public static RemoteClient newRemoteClient(String serverId) {
		return new RemoteClient(serverId);
	}
	
	public void sync() {
		ServerListSyncPacket pk = new ServerListSyncPacket();
		pk.serverList = new ArrayList<String>();
		JBConnector.getInstance().getSessionManager().sendPacketWithResponse(pk, new Callback() {
			public void accept(Object... o) {
				ServerListSyncPacket p = (ServerListSyncPacket)o[0];
				for(String servers : p.serverList) {
					serverList.add(servers);
				}
			}
		});
	}
	
	public void reload() {
		ServerListSyncPacket pk = new ServerListSyncPacket();
		pk.serverList = new ArrayList<String>();
		JBConnector.getInstance().getSessionManager().sendPacketWithResponse(pk, new Callback() {
			public void accept(Object... o) {
				ServerListSyncPacket p = (ServerListSyncPacket)o[0];
				serverList.clear();
				for(String servers : p.serverList) {
					serverList.add(servers);
				}
			}
		});
	}
	
	public static ArrayList<String> getAllClientList(){
		return serverList;
	}
}