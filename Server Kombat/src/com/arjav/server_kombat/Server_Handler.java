package com.arjav.server_kombat;

import java.awt.Component;
import java.awt.Graphics;
import java.util.LinkedList;

import com.arjav.server_kombat.ground.Server_Grass;
import com.arjav.server_kombat.ground.Server_Ground;
import com.arjav.server_kombat.player.Server_Hand;
import com.arjav.server_kombat.player.Server_HealthBar;
import com.arjav.server_kombat.player.Server_Leg;
import com.arjav.server_kombat.player.Server_Limb;
import com.arjav.server_kombat.player.Server_Player;

public class Server_Handler {

	public Server_Game game;
	public LinkedList<Server_Ground> ground = new LinkedList<Server_Ground>();
	public LinkedList<Server_Player> players = new LinkedList<Server_Player>();
	public LinkedList<Server_Limb> limbs = new LinkedList<Server_Limb>(); 
	public Server_HealthBar hb1, hb2;

	public Server_Handler(Server_Game game) {
		this.game = game;
	}
	
	public Server_Player getPlayer(String name) {
		for(int i = 0 ; i < players.size() ; i++) if(players.get(i).name==name) return players.get(i);
		return null;
	}
	
	public void init() {
		players.get(0).init();
		players.get(1).init();
		for(int i = 0 ; i < limbs.size() ; i++) {
			Server_Limb l = limbs.get(i);
			if(l.getId()==Server_Id.hand) {
				Server_Hand h = (Server_Hand) l;
				h.init();
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0 ; i < ground.size(); i++) {
			Server_Ground gro = ground.get(i);
			gro.render(g);
		}
		for(int b = 0 ; b < limbs.size(); b++) {
			Server_Limb l = limbs.get(b);
			l.render(g);
		}
		for(int i = 0 ; i < players.size() ; i++) {
			Server_Player p = players.get(i);
			p.render(g);
		}
		hb1.render(g);
		hb2.render(g);
	}
	
	public void tick() {
		for(int i = 0 ; i < ground.size(); i++) {
			Server_Ground gro = ground.get(i);
			gro.tick();
		}
		for(int b = 0 ; b < limbs.size(); b++) {
			Server_Limb l = limbs.get(b);
			l.tick();
		}
		for(int a = 0 ; a < players.size(); a++) {
			Server_Player p = players.get(a);
			p.tick();
		}
		hb1.tick();
		hb2.tick();
	}
	
	private void addGround(Server_Ground gr) {
		ground.add(gr);
	}
	
	private void addPlayer(Server_Player pr) {
		players.add(pr);
	}
	
	private void addLimb(Server_Limb l) {
		limbs.add(l);
	}
	
	public void removePlayer(Server_Player p) {
		players.remove(p);
		for(int i = 0 ; i < limbs.size() ; i++) {
			Server_Limb l = limbs.get(i);
			if(l.playerNum==p.playerNum) limbs.remove(l);
		}
	}
	
	public void createLevel() {
		for(int a = 0 ; a < 11 ; a++) {
			
			if(a==4) {
				for(int b = 0 ; b <= 1 ; b++) {
					if(b==0) {
						Server_Player p = new Server_Player(64, 64, 64, 96, Server_Id.player, game, 0, "green");
						addPlayer(p);
						addLimb(new Server_Hand(p, 0, 0, 32, 16, Server_Id.hand, true, 0, game, true));
						addLimb(new Server_Hand(p, 0, 0, 32, 16, Server_Id.hand, false, 0, game, false));
						addLimb(new Server_Leg(p, p.x, p.y + p.height, 64, 32, Server_Id.legs, 0, game));
						hb1 = new Server_HealthBar(this, p, 32, 32, 300, 32);
					}
					else {
						Server_Player p = new Server_Player(Server_Game.getWidth()-128, 64, 64, 96, Server_Id.player, game, 1, "red");
						addPlayer(p);
						addLimb(new Server_Hand(p, 0, 0, 32, 16, Server_Id.hand, true, 1, game, true));
						addLimb(new Server_Hand(p, 0, 0, 32, 16, Server_Id.hand, false, 1, game, false));
						addLimb(new Server_Leg(p, p.x, p.y + p.height, 64, 32, Server_Id.legs, 1, game));
						hb2 = new Server_HealthBar(this, p, Server_Game.getWidth()-332, 32, 300, 32);
					}
				}
			}
			
			if(a==11-1){
				for(int i = 0 ; i < 22 ; i++) {
				addGround(new Server_Grass(i*32, Server_Game.getHeight()-32, 32, 32, Server_Id.grass, game));
				}
			}
		}
	}

}
