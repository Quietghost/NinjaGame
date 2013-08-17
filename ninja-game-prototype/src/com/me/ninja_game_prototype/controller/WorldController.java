package com.me.ninja_game_prototype.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.me.ninja_game_prototype.helper.LevelLoader;
import com.me.ninja_game_prototype.view.WorldView;

public class WorldController
{
	static Music song = Gdx.audio.newMusic(Gdx.files.internal("data/music/asian_duet.mp3"));
	
	public WorldController()
	{
		LevelLoader.get().loadLevel(0);
		WorldView.get().init();
	}
	
	public static void playMusic(boolean looping){
		song.setLooping(looping);
		song.setVolume(0.3f);
		song.play();
	}
	
	public static void stopsMusic(){
		song.stop();
	}

	
}
