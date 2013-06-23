package com.me.ninja_game_prototype.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {

	private Audio(){}
	
	public static Music song = Gdx.audio.newMusic(Gdx.files.internal("data/asian_duet.mp3"));
	public static Sound walk = Gdx.audio.newSound(Gdx.files.internal("data/walk_wood_normalspeed.mp3"));
	public static Sound hit = Gdx.audio.newSound(Gdx.files.internal("data/hit_ouch.mp3"));
	public static Sound exit = Gdx.audio.newSound(Gdx.files.internal("data/celebration.mp3"));
	
	public static void playMusic(boolean looping){
		song.setLooping(looping);
		song.setVolume(0.3f);
		song.play();
	}
	
	public static void stopsMusic(){
		song.stop();
	}
	
	public static void walk(){
		long id = walk.play(1.0f);
		walk.setLooping(id, true);
	}
	
	public static void stopWalk(){
		walk.stop();
	}
	
	public static void hitObstacle(){
		hit.play();
	}
	
	public static void stopHitObstacle(){
		hit.stop();
	}
	
	public static void playExit(){
		exit.play();
	}
	
	public static void stopExit(){
		exit.stop();
	}
}
