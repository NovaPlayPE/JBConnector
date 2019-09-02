package net.novaplay.jbconnector.nukkit.client;

import lombok.Getter;
import lombok.Setter;

public class Client {
	
	private String serverId;
	private String address;
	private int port;
	@Getter
	@Setter
	public boolean isOnline;
	
	public Client(String serverId, String address, int port) {
		this.serverId = serverId;
		this.address = address;
		this.port = port;
		this.setOnline(true);
	}

}