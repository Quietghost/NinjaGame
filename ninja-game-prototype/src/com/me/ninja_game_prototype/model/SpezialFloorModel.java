package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.math.Polygon;

public class SpezialFloorModel
{
	public static final String TYPE_NONE = "none";
	public static final String TYPE_STONE = "Stone";
	
	private String type;
	
	public SpezialFloorModel(String name, Polygon area)
	{
		String[] tmp = name.split("_");
		
		switch (tmp[0]) {
		case TYPE_STONE:
			type = TYPE_STONE;
			break;
		default:
			type = TYPE_NONE;
		}
	}

	public String getType() {
		return type;
	}
}
