package com.arjav.client_kombat.player;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.arjav.client_kombat.Client_Game;
import com.arjav.client_kombat.Client_Id;

public abstract class Client_Limb {
	
	Client_Player player;
	Client_Id id;
	public int x;
	public int y;
	int width;
	int height;
	public int playerNum;
	Client_Game game;
	
	public Client_Limb(Client_Player player, int x, int y, int width, int height, Client_Id id, int playerNum, Client_Game game) {
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
	
	public Client_Id getId() {
		return id;
	}
	
}
