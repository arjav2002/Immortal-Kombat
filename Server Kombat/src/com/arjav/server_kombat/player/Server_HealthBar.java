package com.arjav.server_kombat.player;

import java.awt.Color;
import java.awt.Graphics;

import com.arjav.server_kombat.Server_Handler;
import com.arjav.server_kombat.Server_ResultDisplay;
import com.arjav.server_kombat.assets.Server_Sound;

public class Server_HealthBar {
	
	int playerNum, x, y;
	public int width, health;
	int height;
	Server_Handler handler;
	Server_Player player;
	Server_ResultDisplay rd ;
	public boolean won = false;
	public boolean resultDisplayed = false;
	
	public Server_HealthBar(Server_Handler handler, Server_Player player, int x, int y, int width, int height) {
		playerNum = player.playerNum ;
		this.x = x;
		this.y = y;
		this.width = width;
		health = width * 3;
		this.height = height;
		this.handler = handler;
		this.player = player;
	}
	
	public void tick() {
		width = (int) ((1 / 3.0) * health);
		if(health<=0 && !resultDisplayed) {
			handler.removePlayer(player);
			handler.game.frame.setVisible(false);
			if(playerNum==0) {
				rd = new Server_ResultDisplay("red");
				won = true;
				Server_Sound.playSound("file:///C:/Desktop/SubzeroWins.wav");
				handler.game.sendMessage("you lose");
			}
			else if(playerNum==1) rd = new Server_ResultDisplay("green");
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
