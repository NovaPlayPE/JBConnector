package net.novaplay.jbconnector.nukkit.player;

import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.jbconnector.nukkit.client.Client;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.player.ChatPacket;
import net.novaplay.networking.player.KickPacket;
import net.novaplay.networking.player.TransferPacket;
import net.novaplay.networking.server.PlayerInfoPacket;

import java.util.*;

public class JBPlayer {
	
	private String username = null;
	private UUID uuid = null;
	private String serverId = null;
	private boolean online = false;
	
	public JBPlayer(String nickname) {
		this.username = nickname;
		PlayerInfoPacket pk = new PlayerInfoPacket();
		JBConnector.getInstance().getSessionManager().sendPacketWithResponse(pk, new Callback() {

			@Override
			public void accept(Object... args) {
				PlayerInfoPacket p = (PlayerInfoPacket)args[0];
				if(p.handled) {
					if(p.online) {
						online = true;
						uuid = p.uuid;
						serverId = p.serverId;
					} else {
						online = false;
					}
				}
				
			}
			
		});
	}
	
	public void transfer(String client) {
		TransferPacket pk = new TransferPacket();
		pk.player = username;
		pk.destination = client;
		pk.handled = false;
		JBConnector.getInstance().getSessionManager().sendPacket(pk);
	}
	
	public void sendMessage(String message) {
		sendChat(message,ChatPacket.CHAT);
	}
	
	public void sendTip(String tip) {
		sendChat(tip,ChatPacket.TIP);
	}
	
	public void sendPopup(String popup) {
		sendChat(popup,ChatPacket.POPUP);
	}
	
	public void sendTitle(String title) {
		sendChat(title,ChatPacket.TITLE);
	}
	
	public void sendChat(String text, String type) {
		ChatPacket pk = new ChatPacket();
		pk.player = username;
		pk.type = type;
		pk.message = text;
		pk.handled = false;
		JBConnector.getInstance().getSessionManager().sendPacket(pk);
	}
	
	public void kick(String reason) {
		KickPacket pk = new KickPacket();
		pk.player = username;
		pk.reason = reason;
		pk.type = "kick";
		pk.handled = false;
		JBConnector.getInstance().getSessionManager().sendPacket(pk);
	}
	
	public void setServerId(String c) {
		this.serverId = c;
	}
	
	public String getName() {
		return this.username;
	}
	
	public UUID getUniqueId() {
		return this.uuid;
	}
	
	public String getServerId() {
		return this.serverId;
	}
	
	public boolean isOnline() {
		return this.online;
	}
}
