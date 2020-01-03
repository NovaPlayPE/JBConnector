package net.novaplay.jbconnector.nukkit.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;

import net.novaplay.networking.player.LoginPacket;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void playerPreLogin(PlayerPreLoginEvent e) {
		LoginPacket pk = new LoginPacket();
		pk.username = e.getPlayer().getName();
	}

}
