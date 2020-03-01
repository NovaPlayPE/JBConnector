package net.novaplay.jbconnector.nukkit.response;

import java.util.ArrayList;

import net.novaplay.library.callback.Callback;
import net.novaplay.networking.server.ServerListSyncPacket;

public class GetOnlineClientsResponse implements Callback{

	public ArrayList<String> clients = new ArrayList<String>();
	
	@Override
	public void accept(Object... args) {
		ServerListSyncPacket pk = (ServerListSyncPacket)args[0];
		for(String servers : pk.serverList) {
			clients.add(servers);
		}
	}
	
	public ArrayList<String> getClients(){
		return clients;
	}

}
