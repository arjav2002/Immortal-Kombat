package com.arjav.client_kombat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Client_ResultDisplay extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Font font;
	String player = "";
	
	public Client_ResultDisplay(String player) {
		setSize(350, 300);
		setTitle(player + " won!");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		font = new Font("Serif", Font.PLAIN, 50);
		this.player = player;
	}
	
	
	public void paint(Graphics g) {
		String message = "";
		if(player.equals("red")) {
			g.setColor(Color.red);
			message = "RED WINS";
		}
		else if(player.equals("green")) {
			g.setColor(Color.green);
			message = "GREEN WINS";
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString("FATALITY", 50, 100);
		if(message.equals("RED WINS"))g.drawString(message, 50, 200);
		else if(message.equals("GREEN WINS")) g.drawString(message, 25, 200);
	}

}
