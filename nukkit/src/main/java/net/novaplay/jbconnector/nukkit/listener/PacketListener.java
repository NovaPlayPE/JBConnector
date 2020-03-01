package net.novaplay.jbconnector.nukkit.listener;

import java.net.InetSocketAddress;

import cn.nukkit.Player;
import cn.nukkit.Server;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.jbconnector.nukkit.client.ClientManager;
import net.novaplay.jbconnector.nukkit.client.RemoteClient;
import net.novaplay.library.netty.packet.Packet;
import net.novaplay.networking.IPlayerPacket;
import net.novaplay.networking.player.*;

public class PacketListener{

	private JBConnector plugin = null;
	
	public PacketListener(JBConnector plugin) {
		this.plugin = plugin;
	}
	
	public void processPacket(Packet packet) {
		handleServerPackets(packet);
		if(packet instanceof IPlayerPacket) {
			handlePlayerPackets(packet);
		}
	}
	
	public void handleServerPackets(Packet packet) {
		
	}
	
	public void handlePlayerPackets(Packet packet) {
		Server.getInstance().getLogger().info("UnknownPacket");
		if(packet instanceof KickPacket) {
			KickPacket pk = (KickPacket)packet;
			Player player = plugin.getServer().getPlayer(pk.player);
			player.close("", pk.reason);
		} else if(packet instanceof ChatPacket) {
			ChatPacket pk = (ChatPacket)packet;
			Player player = plugin.getServer().getPlayer(pk.player);
			String msg = pk.message;
			switch(pk.type) {
			case "chat":
				player.sendMessage(msg);
				break;
			case "tip":
				player.sendTip(msg);
				break;
			case "popup":
				player.sendPopup(msg);
				break;
			case "title":
				player.sendTitle(msg);
				break;
			}
		} else  if(packet instanceof TransferPacket) {
			TransferPacket pk = (TransferPacket)packet;
			Player player = plugin.getServer().getPlayer(pk.player);
			RemoteClient client = ClientManager.newRemoteClient(pk.destination);
			String ip = client.getAddress();
			int port = client.getPort();
			player.transfer(new InetSocketAddress(ip,port));
			return;
		}
	}
	
	
}
