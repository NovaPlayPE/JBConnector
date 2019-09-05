package net.novaplay.jbconnector.nukkit.listener;

import net.novaplay.jbconnector.nukkit.JBConnector;

import cn.nukkit.event.Listener;

public class PacketListener implements Listener {

	private JBConnector plugin = null;
	
	public PacketListener(JBConnector plugin) {
		this.plugin = plugin;
	}
	
}
