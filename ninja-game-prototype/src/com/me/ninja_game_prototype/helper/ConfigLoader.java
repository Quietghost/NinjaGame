package com.me.ninja_game_prototype.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.me.ninja_game_prototype.controller.ConfigController;
import com.me.ninja_game_prototype.model.ConfigModel;

public class ConfigLoader {

	/* static */
	
	/* singleton */
	private static ConfigLoader instance;

	private ConfigLoader() {}

	public static ConfigLoader get()
	{
		if (ConfigLoader.instance == null)
		{
			ConfigLoader.instance = new ConfigLoader();
		}
		return ConfigLoader.instance;
	}
	
	/* instance */
	
public void loadConfig(){
		
		FileHandle file = Gdx.files.internal("data/game_config.txt");
		Json json = new Json();
		
		ConfigModel config = json.fromJson(ConfigModel.class, file);
		ConfigController.get().setConfig(config);
	}
	
	
}
