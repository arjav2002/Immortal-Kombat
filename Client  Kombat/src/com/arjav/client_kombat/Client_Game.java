package com.arjav.client_kombat;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;

import com.arjav.client_kombat.assets.Client_Sound;
import com.arjav.client_kombat.assets.Client_Sprite;
import com.arjav.client_kombat.assets.Client_SpriteSheet;
import com.arjav.client_kombat.input.Client_KeyManager;

public class Client_Game implements Runnable{
	
	public int time = 0;
	public JFrame frame;
	String version = " v0.3";
	Canvas canvas;
	public Client_Handler handler = new Client_Handler(this);
	Graphics g;
	BufferStrategy bs;
	Socket sock;
	OutputStream ostream;
	PrintWriter pw;
	InputStream istream;
	BufferedReader br;
	Thread t = new Thread(this);
	private volatile boolean running = false;
	private static int width = 704 , height = 352 ;
	private static String title = "IMMORTAL KOMBAT- FIGHT! client";
	private Client_SpriteSheet sheet;
	public Client_Sprite greenLegs;
	public Client_KeyManager km;
	
	public Client_Game(String title, int width, int height) throws IOException {
		
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
	public static void main(String[] args) throws IOException {
		Client_Game game = new Client_Game(title, getWidth(), height);
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
	       			System.out.println(frames + " Frames and " + updates + " Updates");
	       			updates = 0;
	       			frames = 0;
	       		}
	        }
	}
	
	private void tick() {
		String info = handler.getPlayer("green").getX() + " " 
					+ handler.getPlayer("green").getY() + " " 
					+ handler.hb2.health + " " 
					+ handler.getPlayer("green").h1.x + " "
					+ handler.getPlayer("green").h1.y + " "
					+ handler.getPlayer("green").h2.x + " "
					+ handler.getPlayer("green").h2.y + " "
					+ handler.getPlayer("green").h1.getPunch() + " "
					+ handler.getPlayer("green").h2.getPunch();
		
		sendMessage(info);
		String infoR = "";
		try {
			infoR = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner s = new Scanner(infoR);
		s.hasNext();
		int a = 0;
		while(s.hasNext()) {
			String str = s.next();
			a++;
			try {
				switch(a) {
				case 1:
					handler.getPlayer("red").x = Integer.parseInt(str);
					break;
				case 2:
					handler.getPlayer("red").y  = Integer.parseInt(str);
					break;
				case 3:
					handler.hb1.health = Integer.parseInt(str);
					break;
				case 4:
					handler.getPlayer("red").h1.x = Integer.parseInt(str);
					break;
				case 5:
					handler.getPlayer("red").h1.y = Integer.parseInt(str);
					break;
				case 6:
					handler.getPlayer("red").h2.x = Integer.parseInt(str);
					break;
				case 7:
					handler.getPlayer("red").h2.y = Integer.parseInt(str);
					break;
				case 8:
					handler.getPlayer("red").h1.setPunch(str.equals("true")?true:false);
					break;
				case 9:
					handler.getPlayer("red").h2.setPunch(str.equals("true")?true:false);
					break;
				}
			} catch(NumberFormatException e) {
				e.printStackTrace();
				frame.setVisible(false);
				str += " " + s.next(); 
				if(str.equals("you lose")) {
					Client_Sound.playSound("file:///C:/Desktop/SubzeroWins.wav");
					new Client_ResultDisplay("red");
				}
				handler.hb1.resultDisplayed = true;
				stop();
			}
		}
		s.close();
		handler.tick();
		Client_Sound.tick();
	}
	
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	private void init() {
		try {
			Scanner s = new Scanner(getClass().getResourceAsStream("/Address.txt"));
			String str = s.nextLine();
			sock = new Socket(str, 1204);
			ostream = sock.getOutputStream();
			pw = new PrintWriter(ostream);
			istream = sock.getInputStream();;
			br = new BufferedReader(new InputStreamReader(istream));
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = new Client_SpriteSheet("/spritesheet.png");
		greenLegs = new Client_Sprite(sheet, 0, 0);
		handler.createLevel();
		km = new Client_KeyManager(handler);
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
		g.drawString("You are green", width / 2 - 40, 20);
		handler.render(g);
		
		bs.show();
		g.dispose();
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		Client_Game.width = width;
	}

	public static int getHeight() {
		return height;
	}
}
