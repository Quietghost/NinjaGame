package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class FloorModel extends Layer
{
	String identifier;
	
	public FloorModel(TiledMapTileLayer tiledMapTileLayer)
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
