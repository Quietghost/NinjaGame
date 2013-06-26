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
					getPosition().add(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
				}else{
					setPosition(new Vector2(getGoal().x - getBounds().width/2, goal.y - getBounds().height/2));
					setVelocity(new Vector2(0, 0));
					GameAudio.stopWalk();
				}
				
				getBounds().x = getPosition().x;
				getBounds().y = getPosition().y;
				break;
			case 2:
					
				getPosition().add(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
				
				getBounds().x = getPosition().x;
				getBounds().y = getPosition().y;
				break;
		}
		
		if (WorldModel.get().getExit().overlaps(this))
		{
			// TODO audio controller
//			if (exit.isAudioFlag()){
//				GameAudio.playExit();
//				exit.setAudioFlag(false);
//			}
			GameModel.get().setGameEnd(true);
		}
		
		if (WorldModel.get().getWall().overlaps(this))
		{
			getPosition().x = preX;
			getPosition().y = preY;
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
	
//	public Rectangle getBounds() {
//		return entityDay.getBounds();
//	}
//	
//	public float getWidth()
//	{
//		return entityDay.getWidth();
//	}
//	
//	public float getHeight()
//	{
//		return entityDay.getHeight();
//	}
//	
//	// TODO ninja day/night size
//	public void setBounds() {/*can't do this until day and night have same size*/}
//	public void setWidth() {/*can't do this until day and night have same size*/}
//	public void setHeight() {/*can't do this until day and night have same size*/}
//	
//	public Vector2 getPosition()
//	{
//		return entityDay.getPosition();
//	}
//	
//	public void setPosition(Vector2 position)
//	{
//		entityDay.setPosition(position);
//		entityNight.setPosition(position);
//	}
//	
//	public Vector2 getVelocity()
//	{
//		return entityDay.getVelocity();
//	}
//	
//	public void setVelocity(Vector2 velocity)
//	{
//		entityDay.setVelocity(velocity);
//		entityNight.setVelocity(velocity);
//	}
//	
//	public float getSpeed()
//	{
//		return entityDay.getSpeed();
//	}
//	
//	public void setSpeed(float speed)
//	{
//		entityDay.setSpeed(speed);
//		entityNight.setSpeed(speed);
//	}
}
