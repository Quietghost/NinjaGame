package com.me.ninja_game_prototype.model;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.audio.GameAudio;

public class NinjaModel extends Observable
{
	private Vector2 goal = new Vector2();
	private int flagInput;
	private boolean moved;
	
	public MovableEntity entity;
	public NinjaModel(float SPEED, float width, float height, Vector2 position) {
		entity = new NinjaEntity(SPEED, width, height, position);
	}

	public void update()
	{
		switch (flagInput)
		{
			case 1:
				if (!entity.bounds.contains(this.getGoal()) && entity.position.dst(goal) > 5)
				{		
					entity.position.add(entity.velocity.cpy().scl(Gdx.graphics.getDeltaTime() * entity.SPEED));
				}else{
					entity.setPosition(new Vector2(goal.x - entity.bounds.width/2, goal.y - entity.bounds.height/2));
					entity.setVelocity(new Vector2(0, 0));
					GameAudio.stopWalk();
				}
				
				entity.bounds.x = entity.position.x;
				entity.bounds.y = entity.position.y;
				break;
			case 2:
					
				entity.position.add(entity.velocity.cpy().scl(Gdx.graphics.getDeltaTime() * entity.SPEED));
				
				entity.bounds.x = entity.position.x;
				entity.bounds.y = entity.position.y;
				break;
		}
		
		setChanged();
		notifyObservers(ObserverMessage.NINJA);
	}
	
	class NinjaEntity extends MovableEntity
	{
		public NinjaEntity(float SPEED, float width, float height, Vector2 position)
		{
			super(SPEED, width, height, position);
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

	public boolean isMoved()
	{
		return moved;
	}

	public void setMoved(boolean moved)
	{
		this.moved = moved;
	}

}
