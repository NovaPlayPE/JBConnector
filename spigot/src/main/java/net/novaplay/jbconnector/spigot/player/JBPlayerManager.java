package net.novaplay.jbconnector.spigot.player;

import java.util.*;
import lombok.Getter;
import lombok.Setter;
	
public class JBPlayerManager {
	
	@Getter
	public static HashMap<String, JBPlayer> players = new HashMap<String,JBPlayer>();
	
	public JBPlayer createPlayer(String nickname) {
		return null; //to do
	}

}
