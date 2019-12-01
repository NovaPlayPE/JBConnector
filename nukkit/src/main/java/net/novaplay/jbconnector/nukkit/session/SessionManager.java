package net.novaplay.jbconnector.nukkit.session;

import java.util.ArrayList;

import cn.nukkit.Server;
import cn.nukkit.scheduler.NukkitRunnable;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.library.callback.Callback;
import net.novaplay.library.netty.ConnectionListener;
import net.novaplay.library.netty.NettyHandler;
import net.novaplay.library.netty.PacketHandler;
import net.novaplay.library.netty.packet.Packet;
import net.novaplay.library.netty.packet.defaultpackets.SetNamePacket;
import net.novaplay.networking.server.ProxyConnectPacket;
import net.novaplay.networking.types.ConnectType;

public class SessionManager {

	private String host = "0.0.0.0";
	private Integer port = 9855;
	private NettyHandler nettyHandler;
	private PacketHandler packetHandler;
	private ConnectionListener connectionListener;
	private ConnectType type;
	private JBConnector plugin;
	private String password = "ABC";
	private String clientID;
	
	private boolean connected = false;

	private ArrayList<Channel> verifiedChannels = new ArrayList<Channel>();
	
	public boolean isConnected() {
		return connected;
	}
	
	public SessionManager(JBConnector plugin, String addressToConnect, int portToConnect, String password, String clientID) {
		this.plugin = plugin;
		this.host = addressToConnect;
		this.port = portToConnect;
		this.password = password;
		this.clientID = clientID;
		type = ConnectType.BEDROCK;
	}
	
	public void startConnection() {
		nettyHandler = new NettyHandler();
		nettyHandler.connectToServer(host, port, new Callback() {
			@Override
            public void accept( Object... args ) {
				plugin.getLogger().info("Connected to proxy");
			}
		});
		
		this.packetHandler = new PacketHandler() {

			@Override
			public void receivePacket(Packet packet, Channel channel) {
				if(packet instanceof ProxyConnectPacket) {
					ProxyConnectPacket pk = (ProxyConnectPacket)packet;
					if(pk.success) {
						connected = true;
					} else {
						
					}
					return;
				}
			}

			@Override
			public void registerPackets() {
				registerPacket(ProxyConnectPacket.class);				
			}
			
		};
		
		this.connectionListener = new ConnectionListener() {

			@Override
			public void channelConnected(ChannelHandlerContext context) {
				plugin.getLogger().info("Connected to proxy");
				SetNamePacket pk = new SetNamePacket();
				pk.setName(clientID);
				sendPacket(pk);
			}

			@Override
			public void channelDisconnected(ChannelHandlerContext arg0) {
				nettyHandler.getNettyClient().scheduleConnect(1500);
			}
		};
		this.nettyHandler.registerPacketHandler(this.packetHandler);
		this.nettyHandler.registerConnectionListener(this.connectionListener);
		
		new NukkitRunnable() {
			public void run() {
				if(!isConnected()) {
					ProxyConnectPacket pk = new ProxyConnectPacket();
					pk.serverId = clientID;
					pk.password = password;
					pk.address = plugin.getServer().getIp();
					pk.port = plugin.getServer().getPort();
					pk.type = type;
					sendPacket(pk);
				}
			}
		}.runTaskTimer(plugin,0,20);
		
	}
	
	public void registerPacket(Class<? extends Packet> packett) {
		if(packetHandler != null && nettyHandler != null) {
			packetHandler.registerPacket(packett);
		}
	}
	
	public void sendPacket(Packet packet) {
		if(packetHandler != null && nettyHandler != null) {
			packetHandler.sendPacket(packet);
		}
	}
	
	public ArrayList<Channel> getVerifiedChannels(){
		return verifiedChannels;
	}
	
}