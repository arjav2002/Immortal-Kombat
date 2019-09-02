package com.arjav.client_kombat.assets;

import java.awt.image.BufferedImage;

public class Client_Sprite {

	Client_SpriteSheet sheet;
	int x, y;
	
	public Client_Sprite(Client_SpriteSheet sheet, int x, int y) {
		this.sheet = sheet;
		this.x = x;
		this.y = y;
	}
	
	public BufferedImage getBufferedImage() {
		return sheet.getSprite(x, y);
	}
	
}
