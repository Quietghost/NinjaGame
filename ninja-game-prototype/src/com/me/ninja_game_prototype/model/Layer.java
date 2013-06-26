package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public abstract class Layer
{
	public Layer(TiledMapTileLayer tiledMapTileLayer)
	{
		layer = tiledMapTileLayer;
	}

	private TiledMapTileLayer layer;

	public TiledMapTileLayer getLayer() {
		return layer;
	}
}
