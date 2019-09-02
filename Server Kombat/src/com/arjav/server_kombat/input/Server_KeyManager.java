package com.arjav.server_kombat.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.arjav.server_kombat.Server_Handler;
import com.arjav.server_kombat.Server_Id;
import com.arjav.server_kombat.player.Server_Hand;
import com.arjav.server_kombat.player.Server_Limb;
import com.arjav.server_kombat.player.Server_Player;

public class Server_KeyManager implements KeyListener{
	
	Server_Player p;
	Server_Hand hu, hd; 
	
	public Server_KeyManager(Server_Handler handler) {
		for(int i = 0 ; i < handler.players.size(); i++) {
			Server_Player p = handler.players.get(i);
			if(p.playerNum==1) this.p = p;
		}
		for(int i = 0 ; i < handler.limbs.size() ; i++) {
			Server_Limb l = handler.limbs.get(i);
			if(l.getId()==Server_Id.hand) {
				Server_Hand h = (Server_Hand) l;
				if(h.playerNum==1) {
					if(h.up) hu = h ;
					else hd = h ;
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if(k==KeyEvent.VK_UP) {
			if(p.falling) return;
			p.setVelY(-20);
			p.falling = true;
		}
		if(k==KeyEvent.VK_RIGHT)
			p.velX = 5 ;
		if(k==KeyEvent.VK_LEFT)
			p.velX = -5;
		if(k==KeyEvent.VK_M)
			hu.setPunch(true);
		if(k==KeyEvent.VK_N)
			hd.setPunch(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if(k==KeyEvent.VK_RIGHT || k==KeyEvent.VK_LEFT)
			p.velX = 0;
		if(k==KeyEvent.VK_M)
			hu.setPunch(false);
		if(k==KeyEvent.VK_N)
			hd.setPunch(false);
			
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// not using
		
	}
	
}
