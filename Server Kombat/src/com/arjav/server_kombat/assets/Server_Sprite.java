package com.arjav.server_kombat.assets;

import java.awt.image.BufferedImage;

public class Server_Sprite {

	Server_SpriteSheet sheet;
	int x, y;
	
	public Server_Sprite(Server_SpriteSheet sheet, int x, int y) {
		this.sheet = sheet;
		this.x = x;
		this.y = y;
	}
	
	public BufferedImage getBufferedImage() {
		return sheet.getSprite(x, y);
	}
	
}
