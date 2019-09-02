package com.arjav.server_kombat.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.arjav.server_kombat.Server_Game;
import com.arjav.server_kombat.Server_Id;
import com.arjav.server_kombat.assets.Server_Sound;
import com.arjav.server_kombat.ground.Server_Ground;

public class Server_Player {

	public int x, y, width, height, playerNum;
	public String name;
	public int velX, velY = 0;
	int lastTime, nowTime, lastHealth, nowHealth;
	public Server_Hand h1, h2;
	public boolean falling = true;
	float gravity = 1.0f;
	Server_Id id;
	Server_Game game;
	
	public void setHand1(Server_Hand h) {
		h1 = h;
	}
	
	public void setHand2(Server_Hand h) {
		h2 = h;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public int getVelY() {
		return velY;
	}
	
	public Server_Player(int x, int y, int width, int height, Server_Id id, Server_Game game, int playerNum, String name) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.game = game;
		this.playerNum = playerNum;
		this.name = name;
	}
	
	public void init() {
		lastTime = game.time;
		lastHealth = game.handler.hb1.health;
	}
	
	public void tick() {
		
		nowTime = game.time;
		nowHealth = game.handler.hb1.health;
		if(nowTime - lastTime >= 2 && playerNum == 1) {
			if(lastHealth - nowHealth >= 100 && !Server_Sound.getPlaying()) {
				Server_Sound.addToQue("file:///C:/Desktop/excellent.wav");
				lastTime = nowTime;
				lastHealth = nowHealth;
			}
			lastTime = nowTime;
			lastHealth = nowHealth;
		}
		
		if(playerNum!=0) {
			x += velX;
			velY += gravity;
			if(falling) y += velY;
		}
		
		if(h1.punch && h1.xOffset >= 30 && h1.right && h1.getBounds().intersects(h1.getOtherPlayer().getBounds())) {
			if(game.handler.hb1.playerNum==h1.getOtherPlayer().playerNum) game.handler.hb1.health -= h1.xOffset ;
			if(game.handler.hb2.playerNum==h1.getOtherPlayer().playerNum) game.handler.hb2.health -= h1.xOffset ;
		}
		else if(h1.punch && h1.xOffset <= -30 && !h1.right && h1.getBounds().intersects(h1.getOtherPlayer().getBounds())) {
			if(game.handler.hb1.playerNum==h1.getOtherPlayer().playerNum) game.handler.hb1.health += h1.xOffset ;
			if(game.handler.hb2.playerNum==h1.getOtherPlayer().playerNum) game.handler.hb2.health += h1.xOffset ;
		}
		if(h2.punch && h2.xOffset >= 30 && h2.right && h2.getBounds().intersects(h2.getOtherPlayer().getBounds())) {
			if(game.handler.hb1.playerNum==h1.getOtherPlayer().playerNum) game.handler.hb1.health -= h2.xOffset ;
			if(game.handler.hb2.playerNum==h1.getOtherPlayer().playerNum) game.handler.hb2.health -= h2.xOffset ;
		}
		else if(h2.punch && h2.xOffset <= -30 && !h2.right && h2.getBounds().intersects(h2.getOtherPlayer().getBounds())) {
			if(game.handler.hb1.playerNum==h1.getOtherPlayer().playerNum) game.handler.hb1.health += h2.xOffset ;
			if(game.handler.hb2.playerNum==h1.getOtherPlayer().playerNum) game.handler.hb2.health += h2.xOffset ;
		}
		
		for(int i = 0 ; i < game.handler.limbs.size() ; i++) {
			Server_Limb l = game.handler.limbs.get(i);
			if(l.getId()==Server_Id.legs && l.playerNum==playerNum) {
				for(int a = 0 ; a < game.handler.ground.size() ; a++) {
					Server_Ground g = game.handler.ground.get(a);
					if(l.getBoundsBottom().intersects(g.getBounds()) && falling && velY > 0) {
						y = Server_Game.getHeight() - 96 - 64;
						falling = false;
						setVelY(0);
					}
				}
			}
		}
		
		for(int i = 0 ; i < game.handler.players.size(); i++) {
			Server_Player p = game.handler.players.get(i);
			if(p.playerNum!=playerNum) {
				for(int a = 0 ; a < game.handler.limbs.size() ; a++) {
					Server_Limb b = game.handler.limbs.get(a);
					if(b.getId()==Server_Id.hand && b.playerNum==playerNum) {
						Server_Hand l = (Server_Hand)b;
						if(p.x > x) l.right = true;
						else if(p.x < x) l.right = false;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(x, y, width, height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
}
