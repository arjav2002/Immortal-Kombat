package com.arjav.server_kombat;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFrame;

import com.arjav.server_kombat.assets.Server_Sound;
import com.arjav.server_kombat.assets.Server_Sprite;
import com.arjav.server_kombat.assets.Server_SpriteSheet;
import com.arjav.server_kombat.input.Server_KeyManager;

public class Server_Game implements Runnable{
	
	public JFrame frame;
	public int time = 0;
	String version = " v0.3";
	Socket sock;
	Canvas canvas;
	public Server_Handler handler = new Server_Handler(this);
	Graphics g;
	ServerSocket sersock;
	BufferedReader br;
	PrintWriter pw;
	BufferStrategy bs;
	Thread t = new Thread(this);
	private volatile boolean running = false;
	private static int width = 704 , height = 352 ;
	private static String title = "IMMORTAL KOMBAT- FIGHT! server";
	public Server_Sprite greenLegs;
	private Server_SpriteSheet sheet;
	public Server_KeyManager km;
	
	
	public Server_Game(String title, int width, int height) throws SocketException, UnknownHostException {
		
		frame = new JFrame(title + version);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(3);
		frame.setLocationRelativeTo(null);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
	
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	public synchronized void start() {
		if(running) return ;
		t.start();
		running = true ;
	}
	
	public synchronized void stop() {
		if(!running) return ;
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false ;
	}
	public static void main(String[] args) throws SocketException, UnknownHostException {
		Server_Game game = new Server_Game(title, getWidth(), height);
		game.start();
	}

	@Override
	public void run() {
		init();
		 long lastTime = System.nanoTime();
	        final double amountOfTicks = 60.0;
	        double ns = 1000000000 / amountOfTicks;
	        double delta = 0;
	        int updates = 0;
	        int frames = 0;
	        long timer = System.currentTimeMillis();

	        while(running){
	        	long now = System.nanoTime();
	        	delta += (now - lastTime) / ns;
	        	lastTime = now;
	        	if(delta >= 1){
	        		tick();
	        		updates++;
	               	delta--;
	        	}
	        	render();
	        	frames++;
	        		
	        	if(System.currentTimeMillis() - timer > 1000){
	       			timer += 1000;
	       			time++;
	       			System.out.println("Ticks: " + updates + ", Frames: " + frames);
	       			updates = 0;
	       			frames = 0;
	       		}
	        }
	}
	
	private void tick() {
		String info = handler.getPlayer("red").getX() + " " + handler.getPlayer("red").getY() + " " + handler.hb1.health + " " + handler.getPlayer("red").h1.x + " " + handler.getPlayer("red").h1.y + " " + handler.getPlayer("red").h2.x + " " + handler.getPlayer("red").h2.y + " " + handler.getPlayer("red").h1.getPunch() + " " + handler.getPlayer("red").h2.getPunch();
		String infoR = "";
		try {
			infoR = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner s = new Scanner(infoR);
		int a = 0;
		while(s.hasNext()) {
			a++;
			String str = s.next();
			try {
				switch(a) {
				case 1:
					handler.getPlayer("green").x = Integer.parseInt(str);
					break;
				case 2:
					handler.getPlayer("green").y  = Integer.parseInt(str);
					break;
				case 3:
					handler.hb2.health = Integer.parseInt(str);
					break;
				case 4:
					handler.getPlayer("green").h1.x = Integer.parseInt(str);
					break;
				case 5:
					handler.getPlayer("green").h1.y = Integer.parseInt(str);
					break;
				case 6:
					handler.getPlayer("green").h2.x = Integer.parseInt(str);
					break;
				case 7:
					handler.getPlayer("green").h2.y = Integer.parseInt(str);
					break;
				case 8:
					if(str.equals("true")) handler.getPlayer("green").h1.setPunch(true);
					else if(str.equals("false")) handler.getPlayer("green").h1.setPunch(false);
					break;
				case 9:
					if(str.equals("true")) handler.getPlayer("green").h2.setPunch(true);
					else if(str.equals("false")) handler.getPlayer("green").h2.setPunch(false);
					break;
				}
			}catch(NumberFormatException e) {
				e.printStackTrace();
				frame.setVisible(false);
				str += " " + s.next(); 
				if(str.equals("you lose")) {
					new Server_ResultDisplay("green");
					Server_Sound.playSound("file:///C:/Desktop/Scorpionwins.wav");
				}
				handler.hb2.resultDisplayed = true;
				stop();
			}
		}
		s.close();
		sendMessage(info);
		handler.tick();
		Server_Sound.tick();
	}
	
	private void init() {
		Server_Sound.addToQue("file:///C:/Desktop/fight.wav");
		try {
			sersock = new ServerSocket(1204);
			sock = sersock.accept();
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(sock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = new Server_SpriteSheet("/spritesheet.png");
		greenLegs = new Server_Sprite(sheet, 0, 0);
		handler.createLevel();
		km = new Server_KeyManager(handler);
		frame.addKeyListener(km);
		handler.init();
	}
	
	private void render() {
		if(bs==null) canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, getWidth(), height);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		g.drawString("You are red", width / 2 - 40, 20);
		handler.render(g);
		
		bs.show();
		g.dispose();
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		Server_Game.width = width;
	}

	public static int getHeight() {
		return height;
	}
}
