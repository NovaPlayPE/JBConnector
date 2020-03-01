package net.novaplay.jbconnector.nukkit.player;

import java.util.*;
import lombok.Getter;
import lombok.Setter;
import net.novaplay.jbconnector.nukkit.JBConnector;
import net.novaplay.jbconnector.nukkit.client.Client;
import net.novaplay.library.callback.Callback;
import net.novaplay.networking.server.PlayerInfoPacket;
import net.novaplay.networking.player.*;
	
public class JBPlayerManager {
	
	public static JBPlayer newPlayer(String nickname) {
		return new JBPlayer(nickname);
	}
	
}
