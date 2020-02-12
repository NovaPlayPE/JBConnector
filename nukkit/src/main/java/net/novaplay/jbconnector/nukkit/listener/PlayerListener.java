package net.novaplay.jbconnector.nukkit.listener;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.networking.player.*;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void playerPreLogin(PlayerPreLoginEvent e) {
		LoginPacket pk = new LoginPacket();
		pk.username = e.getPlayer().getName();
		pk.uuid = e.getPlayer().getUniqueId();
		pk.serverId = JBConnector.getInstance().getServerId();
		JBConnector.getInstance().getSessionManager().sendPacket(pk);
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		if(!e.getReason().equals("JBConnector.transfer")) {
			LogoutPacket pk = new LogoutPacket();
			pk.username = e.getPlayer().getName();
			pk.uuid = e.getPlayer().getUniqueId();
			pk.reason = e.getReason();
			JBConnector.getInstance().getSessionManager().sendPacket(pk);
		}
	}

}
