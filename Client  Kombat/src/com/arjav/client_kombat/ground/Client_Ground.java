package com.arjav.client_kombat.ground;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.arjav.client_kombat.Client_Game;
import com.arjav.client_kombat.Client_Id;

public abstract class Client_Ground {

	public int x, y, width, height;
	Client_Id id;
	Client_Game game;
	
	public Client_Ground(int x, int y, int width, int height, Client_Id id, Client_Game game) {
		this.x = x ;
		this.y = y ; 
		this.width = width;
		this.height = height ;
		this.id = id ;
		this.game = game ;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
