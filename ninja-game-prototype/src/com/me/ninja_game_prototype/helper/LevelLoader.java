package com.me.ninja_game_prototype.helper;

import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.model.ExitModel;
import com.me.ninja_game_prototype.model.NinjaModel;
import com.me.ninja_game_prototype.model.ObstacleModel;
import com.me.ninja_game_prototype.model.WorldModel;

public class LevelLoader {
	/* static */
	
	/* singleton */
	private static LevelLoader instance;

	private LevelLoader() {
	}

	public static LevelLoader get() {
		if (LevelLoader.instance == null) {
			LevelLoader.instance = new LevelLoader();
		}
		return LevelLoader.instance;
	}
	
	/* instance */
	public void loadLevel(int level)
	{
		WorldModel.get().clear();
		
		// TODO load tmx file
//        TiledMap map;
//        AtlasTmxMapLoader atlas;
//        
//        // Load the tmx file into map
//        map = new TmxMapLoader().load("map2.tmx");
		
		WorldModel.get().addObstacles(new ObstacleModel(new Vector2(320,310), 64, 64));
		WorldModel.get().addObstacles(new ObstacleModel(new Vector2(100,110), 108, 92));
		
		WorldModel.get().setNinja(new NinjaModel(60, 54, 62, new Vector2(200,400)));
		
		WorldModel.get().setExit(new ExitModel(new Vector2(600,110), 54, 100));
	}
}
