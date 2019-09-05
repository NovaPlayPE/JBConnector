package net.novaplay.jbconnector.nukkit.client;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

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
	
	public String getServerId() { return this.serverId; }
	public String getAddress() { return this.address; }
	public int getPort() {return this.port; }

}