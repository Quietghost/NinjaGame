package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class WallModel extends Entity
{
	String identifier;
	
	public WallModel(Vector2 position, float width, float height)
	{
		super(position, width, height);
	}
	
	public WallModel(TiledMapTileLayer tiledMapTileLayer)
	{
		// TODO constructor
		this(new Vector2(0,0), 0, 0);
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
