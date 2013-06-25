package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;

public class ObstacleModel extends ObjectModel
{
	private String identifier;
	private boolean rumble;
	private boolean audioFlag = true;
	
	public ObstacleModel(Vector2 position, float width, float height, Texture texture)
	{
		super(position, width, height);
		this.setTexture(texture);
	}
	
	public ObstacleModel(MapObject o)
	{
		super(o);
	}

	public void update()
	{
	}
	
	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public boolean isRumble()
	{
		return rumble;
	}

	public void setRumble(boolean rumble)
	{
		this.rumble = rumble;
	}

	public boolean isAudioFlag()
	{
		return audioFlag;
	}

	public void setAudioFlag(boolean audioFlag)
	{
		this.audioFlag = audioFlag;
	}
}
