package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.audio.GameAudio;
import com.me.ninja_game_prototype.helper.MapObjectDateGatherer;

public class NinjaModel extends MovableEntity
{
	private Vector2 goal = new Vector2();
	private Vector2 startPosition = new Vector2();
	private int flagInput;
	private Texture nightTexture;
	
	public NinjaModel(MapObject day, MapObject night)
	{
		super(0, new Vector2(0,0), 0, 0);
		
		MapObjectDateGatherer.gather(night, this);
		this.nightTexture = getTexture();
		
		float w = getWidth(); float h = getHeight();
		MapObjectDateGatherer.gather(day, this);
		
		// Do ninja textures day and night have different dimensions?
		if (getWidth()!=w || getHeight()!=h) System.exit(0);
		
		this.startPosition = new Vector2(getPosition().x, getPosition().y);
	}

	public void update()
	{
		float preX = getPosition().x;
		float preY = getPosition().y;
		
		switch (flagInput)
		{
			case 1:
				if (!getBounds().contains(getGoal()) && getPosition().dst(getGoal()) > 5)
				{		
					addPosition(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
				}else{
					setPosition(new Vector2(getGoal().x - getBounds().width/2, goal.y - getBounds().height/2));
					setVelocity(new Vector2(0, 0));
					GameAudio.stopWalk();
				}
				break;
			case 2:
				addPosition(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
				break;
		}
		
		// no ghost
		if (WorldModel.get().getWall().overlaps(this))
		{
			setPosition(preX, preY);
		}

		// Exit?
		if (WorldModel.get().getExit().overlaps(this))
		{
			// TODO audio controller
//			if (exit.isAudioFlag()){
//				GameAudio.playExit();
//				exit.setAudioFlag(false);
//			}
			GameModel.get().setGameEnd(true);
		}

		setChanged();
		notifyObservers(ObserverMessage.NINJA);
	}

	public Vector2 getGoal()
	{
		return goal;
	}

	public void setGoal(Vector2 goal)
	{
		
		this.goal = goal;
	}

	public int getFlagInput()
	{
		return flagInput;
	}

	public void setFlagInput(int flagInput)
	{
		this.flagInput = flagInput;
	}

	public Texture getNightTexture()
	{
		return nightTexture;
	}

	public void setNightTexture(Texture nightTexture)
	{
		this.nightTexture = nightTexture;
	}

	public void resetStartPosition() {
		setPosition(startPosition.x, startPosition.y);
	}
}
