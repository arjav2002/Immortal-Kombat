package com.arjav.server_kombat.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.arjav.server_kombat.Server_Game;
import com.arjav.server_kombat.Server_Id;

public class Server_Hand extends Server_Limb{

	public int xOffset, playerNum;
	public boolean right, up;
	boolean punch;
	int lastXdiff, nowXdiff;
	Server_Player p, p2;
	
	public boolean getPunch() {
		return punch;
	}
	
	public void setPunch(boolean punch) {
		this.punch = punch;
	}
	
	public Server_Hand(Server_Player player, int x, int y, int width, int height, Server_Id id, boolean right, int playerNum, Server_Game game, boolean up) {
		super(player, x, y, width, height, id, playerNum, game);
		if(right)
			x = player.x + player.width;
		else 
			x = player.x - width;
		this.up = up;
		this.right = right;
		this.playerNum = playerNum;
	}
	
	public void init() {
		for(int i = 0 ; i < game.handler.players.size() ; i++) {
			Server_Player pl = game.handler.players.get(i);
			if(pl.playerNum==playerNum) p = pl;
			else p2 = pl;
		}
		if(up) p.setHand1(this);
		else p.setHand2(this);
	}

	public Server_Player getOtherPlayer(){
		return p2 ;
	}
	
	@Override
	public void tick() {
		nowXdiff = p.x - x;
		if(punch) {
			if(right) x = player.x + player.width + 30;
			else x = player.x - width - 30;
			if(up) y = player.y + player.height/2 ;
			else y = player.y + player.height/2 - height * 2;
		}
		else {
			if(right) x = player.x + player.width;
			else x = player.x - width ;
			if(up) y = player.y + player.height/2 ;
			else y = player.y + player.height/2 - height * 2;
		}
		lastXdiff = p.x - x;
		xOffset = nowXdiff - lastXdiff ;
	}

	@Override
	public void render(Graphics g) {
		if(playerNum==0) g.setColor(Color.green);
		else if(playerNum==1) g.setColor(Color.red);
		g.fillRect(x, y, width, height);
		
	}
	
	public Rectangle getBounds() {
		 return new Rectangle(x, y, width, height);
	}
	
	

}
