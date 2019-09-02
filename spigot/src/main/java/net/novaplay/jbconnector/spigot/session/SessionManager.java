package net.novaplay.jbconnector.spigot.session;

import java.util.ArrayList;

import io.netty.channel.Channel;
import net.novaplay.jbconnector.spigot.JBConnector;
import net.novaplay.library.callback.Callback;
import net.novaplay.library.netty.NettyHandler;
import net.novaplay.library.netty.PacketHandler;

import net.novaplay.networking.types.ConnectType;

public class SessionManager {

	private String host = "0.0.0.0";
	private Integer port = 25565;
	private NettyHandler nettyHandler;
	private PacketHandler packetHandler;
	private ConnectType type;
	private JBConnector plugin;

	private ArrayList<Channel> verifiedChannels = new ArrayList<Channel>();
	
	public SessionManager(JBConnector plugin, String addressToConnect, int portToConnect) {
		this.plugin = plugin;
		this.host = addressToConnect;
		this.port = portToConnect;
		type = ConnectType.JAVA;
	}
	
	public void startConnection() {
		nettyHandler = new NettyHandler();
		nettyHandler.connectToServer(host, port, new Callback() {
			@Override
            public void accept( Object... args ) {}
		});
	}
	
}
