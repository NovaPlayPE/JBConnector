package net.novaplay.jbconnector.nukkit.client;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.server.ServerInfoPacket;

public class Client {
	
	private String serverId;
	private String address;
	private int port;
	@Getter
	@Setter
	public boolean isOnline;
	private ArrayList<String> players = new ArrayList<String>(); //not sure, that it will be always
	
	public Client(String serverId, String address, int port) {
		this.serverId = serverId;
		this.address = address;
		this.port = port;
		this.setOnline(true);
	}
	
	public void setPlayers(ArrayList<String> pla) {this.players = pla;}
	public ArrayList<String> getPlayers() { return this.players; }
	public String getServerId() { return this.serverId; }
	public String getAddress() { return this.address; }
	public int getPort() {return this.port; }
	
	public void update() {
		ServerInfoPacket pk = new ServerInfoPacket();
		pk.serverId = getServerId();
		JBConnector.getInstance().getSessionManager().sendPacketWithResponse(pk, new Callback() {
			public void accept(Object... o) {
				ServerInfoPacket p = (ServerInfoPacket)o[0];
				setPlayers(p.players);
			}
		});
	}

}