package net.novaplay.jbconnector.nukkit.player;

import net.novaplay.jbconnector.nukkit.client.Client;

import java.util.*;

public class JBPlayer {
	
	private String username = null;
	private UUID uuid = null;
	private Client client = null;
	
	public JBPlayer(String nickname, UUID uuid) {
		this.username = nickname;
		this.uuid = uuid;
	}
	
	public void transfer(Client client) {
		
	}
	
	public void sendMessage(String message) {
		
	}
	
	public void kick(String reason) {
		
	}
	
	public void ban(String reason) {
		this.ban(reason, new String[]{"all"});
	}
	
	public void ban(String reason, String[] clients) {
		
	}
	
	public String getName() {
		return this.username;
	}
	
	public UUID getUniqueId() {
		return this.uuid;
	}
	
	public Client getCurrentClient() {
		return this.client;
	}
}
