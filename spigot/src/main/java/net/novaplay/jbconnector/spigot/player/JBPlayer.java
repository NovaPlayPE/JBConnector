package net.novaplay.jbconnector.spigot.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.novaplay.library.netty.packet.Packet;

public class JBPlayer {
	
	private Player player = null;
	public UUID uuid = null;
	
	public JBPlayer(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
	}
	
	public void receivePacket(Packet packet) {
		
	}
	
	public void sendPacket(Packet packet) {
		
	}

}
