package com.arjav.server_kombat.ground;

import java.awt.Color;
import java.awt.Graphics;

import com.arjav.server_kombat.Server_Game;
import com.arjav.server_kombat.Server_Id;

public class Server_Grass extends Server_Ground{

	public Server_Grass(int x, int y, int width, int height, Server_Id id, Server_Game game) {
		super(x, y, width, height, id, game);
	}

	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
		
	}

}
