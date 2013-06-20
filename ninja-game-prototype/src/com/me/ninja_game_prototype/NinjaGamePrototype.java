package com.me.ninja_game_prototype;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.me.ninja_game_prototype.screens.SplashScreen;

public class NinjaGamePrototype extends Game {
	
	public static final String VERSION = "0.0.1 PreAlpha";
	public static final String LOG = "Panpipe Ninja";
	public static boolean DEBUG = false;
	public static int ATTEMPTS = 0;
	
	FPSLogger log;
	
	@Override
	public void create() {	
		log = new FPSLogger();
		
		// TODO App-Controller here
		setScreen(new SplashScreen(this));
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

	public int getATTEMPTS() {
		return ATTEMPTS;
	}

	public void addATTEMPTS() {
		ATTEMPTS += 1;
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
