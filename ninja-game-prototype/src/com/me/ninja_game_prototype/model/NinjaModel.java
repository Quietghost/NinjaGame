package com.me.ninja_game_prototype.model;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.audio.GameAudio;

public class NinjaModel extends Observable
{
	private Vector2 goal = new Vector2();
	private int flagInput;
	
	public MovableEntity entityDay;
	public MovableEntity entityNight;
	public ObjectModel day;
	public ObjectModel night;
	
	public NinjaModel(MapObject day, MapObject night)
	{
		this.day = new ObjectModel(day);
		this.night = new ObjectModel(night);
		
		entityDay = new NinjaEntity(0, this.day.getWidth(), this.day.getHeight(), this.day.getPosition());
		entityNight = new NinjaEntity(0, this.night.getWidth(), this.night.getHeight(), this.night.getPosition());
	}

	public void update()
	{
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
		
		setChanged();
		notifyObservers(ObserverMessage.NINJA);
	}
	
	class NinjaEntity extends MovableEntity
	{
		public NinjaEntity(float speed, float width, float height, Vector2 position)
		{
			super(speed, position, width, height);
		}
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

	public Rectangle getBounds() {
		return entityDay.getBounds();
	}
	
	public float getWidth()
	{
		return entityDay.getWidth();
	}
	
	public float getHeight()
	{
		return entityDay.getHeight();
	}
	
	// TODO ninja day/night size
	public void setBounds() {/*can't do this until day and night have same size*/}
	public void setWidth() {/*can't do this until day and night have same size*/}
	public void setHeight() {/*can't do this until day and night have same size*/}
	
	public Vector2 getPosition()
	{
		return entityDay.getPosition();
	}
	
	public void setPosition(Vector2 position)
	{
		entityDay.setPosition(position);
		entityNight.setPosition(position);
	}
	
	public Vector2 getVelocity()
	{
		return entityDay.getVelocity();
	}
	
	public void setVelocity(Vector2 velocity)
	{
		entityDay.setVelocity(velocity);
		entityNight.setVelocity(velocity);
	}
	
	public float getSpeed()
	{
		return entityDay.getSpeed();
	}
	
	public void setSpeed(float speed)
	{
		entityDay.setSpeed(speed);
		entityNight.setSpeed(speed);
	}
}
