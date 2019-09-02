package net.novaplay.jbconnector.spigot.listener;

import org.bukkit.event.Listener;

import net.novaplay.jbconnector.spigot.JBConnector;

public class PacketListener implements Listener{

	private JBConnector plugin = null;
	
	public PacketListener(JBConnector plugin) {
		this.plugin = plugin;
	}
	
}
