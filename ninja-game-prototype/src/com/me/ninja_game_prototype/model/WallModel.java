package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class WallModel extends Layer
{
	String identifier;
	
	public WallModel(TiledMapTileLayer tiledMapTileLayer)
	{
		super(tiledMapTileLayer);
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}
}
