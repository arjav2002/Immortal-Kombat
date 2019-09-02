package com.arjav.client_kombat.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.arjav.client_kombat.Client_Handler;
import com.arjav.client_kombat.Client_Id;
import com.arjav.client_kombat.player.Client_Hand;
import com.arjav.client_kombat.player.Client_Limb;
import com.arjav.client_kombat.player.Client_Player;

public class Client_KeyManager implements KeyListener{
	
	Client_Player p;
	Client_Hand hu, hd; 
	
	public Client_KeyManager(Client_Handler handler) {
		for(int i = 0 ; i < handler.players.size(); i++) {
			Client_Player p = handler.players.get(i);
			if(p.name=="green") this.p = p;
		}
		for(int i = 0 ; i < handler.limbs.size() ; i++) {
			Client_Limb l = handler.limbs.get(i);
			if(l.getId()==Client_Id.hand) {
				Client_Hand h = (Client_Hand) l;
				if(h.playerNum==0) {
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
