package net.novaplay.jbconnector.nukkit.client;

import java.util.ArrayList;

import cn.nukkit.Server;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.server.ServerInfoPacket;

public class RemoteClient {
	
	private String serverId;
	private String address = "";
	private int port = 19132;
	private ArrayList<String> players = new ArrayList<String>();
	
	public RemoteClient(String serverId) {
		this.serverId = serverId;
		ServerInfoPacket pk = new ServerInfoPacket();
		pk.serverId = getServerId();
		JBConnector.getInstance().getSessionManager().sendPacketWithResponse(pk, new Callback() {
			public void accept(Object... o) {
				ServerInfoPacket p = (ServerInfoPacket)o[0];
				address = p.address;
				port = p.port;
				players = p.players;
				Server.getInstance().getLogger().info("Response");
			}
		});
	}
	
	public String getServerId() {
		return this.serverId;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public Integer getPort() {
		return this.port;
	}
	
	public ArrayList<String> getPlayers(){
		return this.players;
	}

}
