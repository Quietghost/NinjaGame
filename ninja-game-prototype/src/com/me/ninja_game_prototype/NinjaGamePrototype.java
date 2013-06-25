package com.me.ninja_game_prototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.me.ninja_game_prototype.screens.GameScreen;

public class NinjaGamePrototype extends Game {
	
	public static final String VERSION = "0.0.1 PreAlpha";
	public static final String LOG = "Panpipe Ninja";
	public static boolean DEBUG = true;
	
	FPSLogger log;
	
	@Override
	public void create() {	
		log = new FPSLogger();
		
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
