package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

public abstract class Layer
{
	public Layer(TiledMapTileLayer tiledMapTileLayer)
	{
		layer = tiledMapTileLayer;
	}

	private TiledMapTileLayer layer;

	public TiledMapTileLayer getLayer()
	{
		return layer;
	}
	
	public boolean overlaps (Entity entity)
	{
		for (int col = 0; col < layer.getWidth(); col++)
		{
			for (int row = 0; row < layer.getHeight(); row++)
			{
				Cell cell = layer.getCell(col, row);
				if (cell==null) continue;
				
				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				
				// pay attention to orthogonal coordinate system
				int y = (Integer)WorldModel.get().getMap().getProperties().get("height")*(Integer)WorldModel.get().getMap().getProperties().get("tileheight")-region.getRegionY();
				
				Rectangle rect = new Rectangle(
						region.getRegionX(),y,
						region.getRegionWidth(),region.getRegionHeight()
				);
				
				System.out.println(rect.toString());
				System.out.println(entity.getBounds().toString());
				
				if (entity.getBounds().overlaps(rect)) return true;
			}
		}
		
		return false;
	}
}
