package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class ObjectModel extends Entity
{
	private Texture texture;
	
	public ObjectModel(Vector2 position, float width, float height)
	{
		super(position, width, height);
	}
	
	public ObjectModel(MapObject o)
	{
		super(new Vector2(0,0), 0, 0);
		
    	this.setPosition(new Vector2((Integer)o.getProperties().get("x"),(Integer)o.getProperties().get("y")));

    	TiledMapTile tile = WorldModel.get().getMap().getTileSets().getTile(Integer.valueOf((String)o.getProperties().get("gid")));
    	this.setHeight(tile.getTextureRegion().getRegionHeight());
    	this.setWidth(tile.getTextureRegion().getRegionWidth());
    	this.texture = tile.getTextureRegion().getTexture();
	}
	
	private String getTexturePath()
	{
		return ((FileTextureData) texture.getTextureData()).getFileHandle().path();
	}
	
	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
}
