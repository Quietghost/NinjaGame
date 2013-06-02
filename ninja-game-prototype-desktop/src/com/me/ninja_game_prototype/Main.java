package com.me.ninja_game_prototype;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "The 36 Chambers of the Panpipe Ninjas " + NinjaGamePrototype.VERSION;
		cfg.useGL20 = true;
		cfg.width = 640;
		cfg.height = 480;
				
		new LwjglApplication(new NinjaGamePrototype(), cfg);
	}
}
