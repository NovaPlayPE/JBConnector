package net.novaplay.jbconnector.nukkit.listener;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.jbconnector.nukkit.client.ClientManager;
import net.novaplay.jbconnector.nukkit.client.RemoteClient;
import net.novaplay.jbconnector.nukkit.player.JBPlayer;
import net.novaplay.jbconnector.nukkit.player.JBPlayerManager;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.player.*;
import net.novaplay.networking.server.PlayerInfoPacket;
import net.novaplay.networking.server.ServerListSyncPacket;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		for(String clients : ClientManager.getAllClientList()) {
			String s = ", ";
			s += clients;
			player.sendMessage("Servers: " + s);
		}
		RemoteClient client = ClientManager.newRemoteClient("Server2");
		player.sendMessage("Port: " + client.getPort().toString());
		player.sendMessage("Address: " + client.getAddress());
		player.sendMessage("Players");
		for(String pla : client.getPlayers()) {
			player.sendMessage("- " + pla);
		}
	}
	
	@EventHandler
	public void playerPreLogin(PlayerPreLoginEvent e) {
		Player p = e.getPlayer();		
		JBPlayer jb = JBPlayerManager.newPlayer(p.getName());
		if(!jb.isOnline()) {
			p.getServer().getLogger().info("Joined");
			LoginPacket login = new LoginPacket();
			login.username = e.getPlayer().getName();
			login.uuid = e.getPlayer().getUniqueId();
			login.serverId = JBConnector.getInstance().getServerId();
			login.handled = false;
			JBConnector.getInstance().getSessionManager().sendPacket(login);
		} else {
			p.getServer().getLogger().info("Failed");
			e.setKickMessage("Already logged in...");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		if(!e.getReason().equals("JBConnector.transfer")) {
			e.getPlayer().getServer().getLogger().info("Logout");
			LogoutPacket pk = new LogoutPacket();
			pk.username = e.getPlayer().getName();
			pk.uuid = e.getPlayer().getUniqueId();
			pk.reason = e.getReason();
			JBConnector.getInstance().getSessionManager().sendPacket(pk);
		}
	}

}
