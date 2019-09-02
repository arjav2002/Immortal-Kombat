package com.arjav.server_kombat.ground;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.arjav.server_kombat.Server_Game;
import com.arjav.server_kombat.Server_Id;

public abstract class Server_Ground {

	public int x, y, width, height;
	Server_Id id;
	Server_Game game;
	
	public Server_Ground(int x, int y, int width, int height, Server_Id id, Server_Game game) {
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
