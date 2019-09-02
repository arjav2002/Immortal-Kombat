package com.arjav.client_kombat;

import java.awt.Graphics;
import java.util.LinkedList;

import com.arjav.client_kombat.ground.Client_Grass;
import com.arjav.client_kombat.ground.Client_Ground;
import com.arjav.client_kombat.player.Client_Hand;
import com.arjav.client_kombat.player.Client_HealthBar;
import com.arjav.client_kombat.player.Client_Leg;
import com.arjav.client_kombat.player.Client_Limb;
import com.arjav.client_kombat.player.Client_Player;

public class Client_Handler {

	public Client_Game game;
	public LinkedList<Client_Ground> ground = new LinkedList<Client_Ground>();
	public LinkedList<Client_Player> players = new LinkedList<Client_Player>();
	public LinkedList<Client_Limb> limbs = new LinkedList<Client_Limb>(); 
	public Client_HealthBar hb1, hb2;

	public Client_Handler(Client_Game game) {
		this.game = game;
	}
	
	public Client_Player getPlayer(String name) {
		for(int i = 0 ; i < players.size() ; i++) if(players.get(i).name.equals(name)) return players.get(i);
		return null;
	}
	
	public void init() {
		for(int i = 0 ; i < players.size() ; i++) {
			Client_Player p = players.get(i);
			p.init();
		}
		for(int i = 0 ; i < limbs.size() ; i++) {
			Client_Limb l = limbs.get(i);
			if(l.getId()==Client_Id.hand) {
				Client_Hand h = (Client_Hand) l;
				h.init();
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0 ; i < ground.size(); i++) {
			Client_Ground gro = ground.get(i);
			gro.render(g);
		}
		for(int b = 0 ; b < limbs.size(); b++) {
			Client_Limb l = limbs.get(b);
			l.render(g);
		}
		for(int a = 0 ; a < players.size(); a++) {
			Client_Player p = players.get(a);
			p.render(g);
		}
		hb1.render(g);
		hb2.render(g);
	}
	
	public void tick() {
		for(int i = 0 ; i < ground.size(); i++) {
			Client_Ground gro = ground.get(i);
			gro.tick();
		}
		for(int b = 0 ; b < limbs.size(); b++) {
			Client_Limb l = limbs.get(b);
			l.tick();
		}
		for(int a = 0 ; a < players.size(); a++) {
			Client_Player p = players.get(a);
			p.tick();
		}
		hb1.tick();
		hb2.tick();
	}
	
	private void addGround(Client_Ground gr) {
		ground.add(gr);
	}
	
	private void addPlayer(Client_Player pr) {
		players.add(pr);
	}
	
	private void addLimb(Client_Limb l) {
		limbs.add(l);
	}
	
	public void removePlayer(Client_Player p) {
		players.remove(p);
		for(int i = 0 ; i < limbs.size() ; i++) {
			Client_Limb l = limbs.get(i);
			if(l.playerNum==p.playerNum) limbs.remove(l);
		}
	}
	
	public void createLevel() {
		for(int a = 0 ; a < 11 ; a++) {
			
			if(a==4) {
				for(int b = 0 ; b <= 1 ; b++) {
					if(b==0) {
						Client_Player p = new Client_Player(64, 64, 64, 96, Client_Id.player, game, 0, "green");
						addPlayer(p);
						addLimb(new Client_Hand(p, 0, 0, 32, 16, Client_Id.hand, true, 0, game, true));
						addLimb(new Client_Hand(p, 0, 0, 32, 16, Client_Id.hand, false, 0, game, false));
						addLimb(new Client_Leg(p, p.x, p.y + p.height, 64, 32, Client_Id.legs, 0, game));
						hb1 = new Client_HealthBar(this, p, 32, 32, 300, 32);
					}
					else {
						Client_Player p = new Client_Player(Client_Game.getWidth()-128, 64, 64, 96, Client_Id.player, game, 1, "red");
						addPlayer(p);
						addLimb(new Client_Hand(p, 0, 0, 32, 16, Client_Id.hand, true, 1, game, true));
						addLimb(new Client_Hand(p, 0, 0, 32, 16, Client_Id.hand, false, 1, game, false));
						addLimb(new Client_Leg(p, p.x, p.y + p.height, 64, 32, Client_Id.legs, 1, game));
						hb2 = new Client_HealthBar(this, p, Client_Game.getWidth()-332, 32, 300, 32);
					}
				}
			}
			
			if(a==11-1){
				for(int i = 0 ; i < 22 ; i++) {
				addGround(new Client_Grass(i*32, Client_Game.getHeight()-32, 32, 32, Client_Id.grass, game));
				}
			}
		}
	}

}
