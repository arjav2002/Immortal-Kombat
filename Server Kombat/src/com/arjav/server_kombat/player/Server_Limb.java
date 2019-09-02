package com.arjav.server_kombat.player;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.arjav.server_kombat.Server_Game;
import com.arjav.server_kombat.Server_Id;

public abstract class Server_Limb {
	
	Server_Player player;
	Server_Id id;
	public int x, y, width, height;
	public int playerNum;
	Server_Game game;
	
	public Server_Limb(Server_Player player, int x, int y, int width, int height, Server_Id id, int playerNum, Server_Game game) {
		this.player = player;
		this.width = width ;
		this.height = height;
		this.x = x;
		this.y = y;
		this.id = id;
		this.playerNum = playerNum;
		this.game = game;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(x + 3, y + height - 3, width - 6, 10);
	}
	
	public Server_Id getId() {
		return id;
	}
	
}
