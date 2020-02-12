package net.novaplay.jbconnector.nukkit.player;

import java.util.*;
import lombok.Getter;
import lombok.Setter;
	
public class JBPlayerManager {
	
	@Getter
	public static HashMap<String, JBPlayer> players = new HashMap<String,JBPlayer>();
	
	public JBPlayer createPlayer(String nickname,UUID uuid) {
		return new JBPlayer(nickname,uuid); //to do
	}

}
