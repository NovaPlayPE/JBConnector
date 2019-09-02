package net.novaplay.jbconnector.nukkit.session;

import java.util.ArrayList;

import io.netty.channel.Channel;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.library.callback.Callback;
import net.novaplay.library.netty.NettyHandler;
import net.novaplay.library.netty.PacketHandler;

import net.novaplay.networking.types.ConnectType;

public class SessionManager {

	private String host = "0.0.0.0";
	private Integer port = 19132;
	private NettyHandler nettyHandler;
	private PacketHandler packetHandler;
	private ConnectType type;
	private JBConnector plugin;

	private ArrayList<Channel> verifiedChannels = new ArrayList<Channel>();
	
	public SessionManager(JBConnector plugin, String addressToConnect, int portToConnect) {
		this.plugin = plugin;
		this.host = addressToConnect;
		this.port = portToConnect;
		type = ConnectType.BEDROCK;
	}
	
	public void startConnection() {
		nettyHandler = new NettyHandler();
		nettyHandler.connectToServer(host, port, new Callback() {
			@Override
            public void accept( Object... args ) {}
		});
	}
	
}