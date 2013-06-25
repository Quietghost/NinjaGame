package com.me.ninja_game_prototype.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.model.Entity;
import com.me.ninja_game_prototype.model.WorldModel;

public class MapObjectDateGatherer
{
	public static void gather(MapObject source, Entity destination)
	{
		TiledMapTile tile = WorldModel.get().getMap().getTileSets().getTile(Integer.valueOf((String)source.getProperties().get("gid")));
    	
		destination.setPosition(new Vector2((Integer)source.getProperties().get("x"),(Integer)source.getProperties().get("y")));
		destination.setBounds(new Rectangle(
			(Integer)source.getProperties().get("x"),
			(Integer)source.getProperties().get("y"), 
			tile.getTextureRegion().getRegionWidth(),
			tile.getTextureRegion().getRegionHeight())
		);

		destination.setHeight(tile.getTextureRegion().getRegionHeight());
		destination.setWidth(tile.getTextureRegion().getRegionWidth());
		destination.setTexture(tile.getTextureRegion().getTexture());
	}
}
