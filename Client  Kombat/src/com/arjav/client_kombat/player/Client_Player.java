package com.arjav.client_kombat.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.arjav.client_kombat.Client_Game;
import com.arjav.client_kombat.Client_Id;
import com.arjav.client_kombat.assets.Client_Sound;
import com.arjav.client_kombat.ground.Client_Ground;

public class Client_Player {

	public int x, y, width, height, playerNum;
	public String name;
	public int velX, velY = 0;
	public Client_Hand h1;
	public Client_Hand h2;
	int nowTime, lastTime;
	int nowHealth, lastHealth;
	public boolean falling = true;
	float gravity = 1.0f;
	Client_Id id;
	Client_Game game;
	
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
	
	public Client_Player(int x, int y, int width, int height, Client_Id id, Client_Game game, int playerNum, String name) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		this.id = id;
		this.game = game;
		this.playerNum = playerNum;
	}
	
	public void setHand1(Client_Hand h) {
		this.h1 = h;
	}
	
	public void setHand2(Client_Hand h) {
		this.h2 = h;
	}
	
	public void init() {
		lastTime = game.time;
		lastHealth = game.handler.hb2.health;
	}
	
	public void tick() {
		
		nowTime = game.time;
		nowHealth = game.handler.hb2.health;
		if(nowTime - lastTime >= 2 && playerNum == 0) {
			if(lastHealth - nowHealth >= 100 && !Client_Sound.getPlaying()) {
				Client_Sound.addToQue("file:///C:/Desktop/excellent.wav");
				lastTime = nowTime;
				lastHealth = nowHealth;
			}
			lastTime = nowTime;
			lastHealth = nowHealth;
		}
		
		if(playerNum != 1) {
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
			Client_Limb l = game.handler.limbs.get(i);
			if(l.getId()==Client_Id.legs && l.playerNum==playerNum) {
				for(int a = 0 ; a < game.handler.ground.size() ; a++) {
					Client_Ground g = game.handler.ground.get(a);
					if(l.getBoundsBottom().intersects(g.getBounds()) && falling && velY > 0) {
						y = Client_Game.getHeight() - 96 - 64;
						falling = false;
						setVelY(0);
					}
				}
			}
		}
		if(falling) y += velY;
		
		for(int i = 0 ; i < game.handler.players.size(); i++) {
			Client_Player p = game.handler.players.get(i);
			if(p.playerNum!=playerNum) {
				for(int a = 0 ; a < game.handler.limbs.size() ; a++) {
					Client_Limb b = game.handler.limbs.get(a);
					if(b.getId()==Client_Id.hand && b.playerNum==playerNum) {
						Client_Hand l = (Client_Hand)b;
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
