package com.arjav.client_kombat.player;

import java.awt.Graphics;

import com.arjav.client_kombat.Client_Game;
import com.arjav.client_kombat.Client_Id;

public class Client_Leg extends Client_Limb{

	int velX = player.velX;
	
	public Client_Leg(Client_Player player, int x, int y, int width, int height, Client_Id id, int playerNum, Client_Game game) {
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
