package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class FloorModel extends Entity
{
	String identifier;
	
	public FloorModel(Vector2 position, float width, float height)
	{
		super(position, width, height);
	}
	
	public FloorModel(TiledMapTileLayer tiledMapTileLayer)
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
