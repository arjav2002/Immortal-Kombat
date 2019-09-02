package com.arjav.server_kombat.player;

import java.awt.Color;
import java.awt.Graphics;

import com.arjav.server_kombat.Server_Game;
import com.arjav.server_kombat.Server_Id;
import com.arjav.server_kombat.ground.Server_Ground;

public class Server_Leg extends Server_Limb{

	int velX = player.velX;
	
	public Server_Leg(Server_Player player, int x, int y, int width, int height, Server_Id id, int playerNum, Server_Game game) {
		super(player, x, y, width, height, id, playerNum, game);
	}

	@Override
	public void tick() {
		x = player.x;
		y = player.y + player.height;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(game.greenLegs.getBufferedImage(), x, y, width, height, null);
		
	}

}
