package net.novaplay.jbconnector.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import net.novaplay.jbconnector.spigot.JBConnector;
import net.novaplay.networking.player.LoginPacket;
import net.novaplay.networking.player.LogoutPacket;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void playerPreLogin(PlayerLoginEvent e) {
		JBConnector.getInstance().getLogger().info("Login ");
		LoginPacket pk = new LoginPacket();
		pk.username = e.getPlayer().getName();
		pk.uuid = e.getPlayer().getUniqueId();
		pk.serverId = JBConnector.getInstance().getServerId();
		JBConnector.getInstance().getSessionManager().sendPacket(pk);
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		//if(!e.getReason().equals("JBConnector.transfer")) {
			JBConnector.getInstance().getLogger().info("Logout ");
			LogoutPacket pk = new LogoutPacket();
			pk.username = e.getPlayer().getName();
			pk.uuid = e.getPlayer().getUniqueId();
			pk.reason = "nil"; // thing I missed is that Spigot doesn't have reason for quit event. I need to do something...
			JBConnector.getInstance().getSessionManager().sendPacket(pk);
		//}
	}

}
