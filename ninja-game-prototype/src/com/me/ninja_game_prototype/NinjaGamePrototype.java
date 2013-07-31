package com.me.ninja_game_prototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.me.ninja_game_prototype.screens.GameScreen;

public class NinjaGamePrototype extends Game {
	
	public static final String VERSION = "0.0.1 PreAlpha";
	public static final String LOG = "Panpipe Ninja";
	public static boolean DEBUG = false;
	public static boolean SHOW_BOUNDINGBOXES = false;
	public static boolean SHOW_ENEMYPATHS = false;
	public static boolean PACK_TEXTURES = false;
	
	FPSLogger log;
	
	@Override
	public void create() {	
		log = new FPSLogger();
		
		// TODO texture-packs
		if (PACK_TEXTURES)
		{
			TexturePacker2.process("../ninja-game-prototype-android/assets/data/animations/", "../ninja-game-prototype-android/assets/data/animations/", "ninja.pack");
		}
		
		// TODO App-Controller here
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
		log.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.render();
	}
}
