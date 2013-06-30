package com.me.ninja_game_prototype.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameAudio {

	private GameAudio(){}
	
	public static Music song = Gdx.audio.newMusic(Gdx.files.internal("data/asian_duet.mp3"));
	public static Sound walk = Gdx.audio.newSound(Gdx.files.internal("data/walk_wood_normalspeed.mp3"));
	public static Sound hit = Gdx.audio.newSound(Gdx.files.internal("data/hit_ouch.mp3"));
	public static Sound exit = Gdx.audio.newSound(Gdx.files.internal("data/celebration.mp3"));
	
	public static Sound pipeTune1 = Gdx.audio.newSound(Gdx.files.internal("data/songs/pipe_tone1.wav"));
	public static Sound pipeTune2 = Gdx.audio.newSound(Gdx.files.internal("data/songs/pipe_tone2.wav"));
	public static Sound pipeTune3 = Gdx.audio.newSound(Gdx.files.internal("data/songs/pipe_tone3.wav"));
	public static Sound pipeTune4 = Gdx.audio.newSound(Gdx.files.internal("data/songs/pipe_tone4.wav"));
	
	public static Sound pipeSong1 = Gdx.audio.newSound(Gdx.files.internal("data/songs/pipe_song1.wav"));
	
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
	
	public static void playPipeTune1(){
		pipeTune1.play();
	}
	public static void playPipeTune2(){
		pipeTune2.play();
	}
	public static void playPipeTune3(){
		pipeTune3.play();
	}
	public static void playPipeTune4(){
		pipeTune4.play();
	}
	public static void playPipeSong1(){
		pipeSong1.play();
	}
}
