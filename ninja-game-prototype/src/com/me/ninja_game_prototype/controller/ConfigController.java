package com.me.ninja_game_prototype.controller;

import com.me.ninja_game_prototype.model.ConfigModel;

public class ConfigController {
	
	/* static */
	
	/* singleton */
	private static ConfigController instance;

	private ConfigController() {}

	public static ConfigController get()
	{
		if (ConfigController.instance == null)
		{
			ConfigController.instance = new ConfigController();
		}
		return ConfigController.instance;
	}
	
	/* instance */
	private ConfigModel config;

	public ConfigModel getConfig() {
		return config;
	}

	public void setConfig(ConfigModel config) {
		this.config = config;
	}
}
