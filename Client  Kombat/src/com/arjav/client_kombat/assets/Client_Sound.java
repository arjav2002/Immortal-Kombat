package com.arjav.client_kombat.assets;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Client_Sound{

	static ArrayList<String> sounds = new ArrayList<String>();
	static Clip ac;
	
	public static boolean getPlaying() {
		if(ac == null || ac.getMicrosecondLength()==ac.getMicrosecondPosition()) return false;
		else return true;
	}
	
	public static void addToQue(String path) {
		sounds.add(path);
	}
	
	public static void playSound(String path) {
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(new URL(path)); // stuff to play audio file
			AudioFormat af = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, af);
			ac = (Clip) AudioSystem.getLine(info);
			ac.open(ais);
			ac.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void tick() {
		try {
			while(sounds.size()!=0) {
				if(ac==null) {
					Client_Sound.playSound(sounds.get(0));
					sounds.remove(0);
				} 
				else if(ac.getMicrosecondPosition()==ac.getMicrosecondLength()) {
					Client_Sound.playSound(sounds.get(0));
					sounds.remove(0);
				}
			}
		}
		catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
}
