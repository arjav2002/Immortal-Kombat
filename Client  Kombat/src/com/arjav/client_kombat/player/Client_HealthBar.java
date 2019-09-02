package com.arjav.client_kombat.player;

import java.awt.Color;
import java.awt.Graphics;

import com.arjav.client_kombat.Client_Handler;
import com.arjav.client_kombat.Client_ResultDisplay;
import com.arjav.client_kombat.assets.Client_Sound;

public class Client_HealthBar {
	
	public int playerNum, x, y, width, height, health;
	Client_Handler handler;
	Client_Player player;
	Client_ResultDisplay rd ;
	public boolean won = false;
	public boolean resultDisplayed = false;
	
	public Client_HealthBar(Client_Handler handler, Client_Player player, int x, int y, int width, int height) {
		playerNum = player.playerNum ;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = width * 3;
		this.handler = handler;
		this.player = player;
	}
	
	public void tick() {
		width = (int)((1/3.0) * health);
		if(health<=0 && !resultDisplayed) {
			handler.removePlayer(player);
			handler.game.frame.setVisible(false);
			if(playerNum==0) rd = new Client_ResultDisplay("red");
			else if(playerNum==1) {
				rd = new Client_ResultDisplay("green");
				won = true;
				Client_Sound.playSound("file:///C:/Desktop/Scorpionwins.wav");
				handler.game.sendMessage("you lose");
			}
			resultDisplayed = true;
			handler.game.stop();
		}
	}
	
	public void render(Graphics g) {
		if(playerNum == 0) g.setColor(Color.green);
		else if(playerNum == 1) g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}

}
