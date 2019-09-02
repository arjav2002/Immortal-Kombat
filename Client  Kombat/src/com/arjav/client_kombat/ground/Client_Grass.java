package com.arjav.client_kombat.ground;

import java.awt.Color;
import java.awt.Graphics;

import com.arjav.client_kombat.Client_Game;
import com.arjav.client_kombat.Client_Id;

public class Client_Grass extends Client_Ground{

	public Client_Grass(int x, int y, int width, int height, Client_Id id, Client_Game game) {
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
