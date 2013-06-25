package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.helper.MapObjectDateGatherer;

public class ObstacleModel extends Entity
{
	private String identifier;
	private boolean rumble;
	private boolean audioFlag = true;
	
	public ObstacleModel(MapObject o)
	{
		super(new Vector2(0,0), 0, 0);
		MapObjectDateGatherer.gather(o, this);
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
