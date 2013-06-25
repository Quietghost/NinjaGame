package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.math.Vector2;

public abstract class MovableEntity extends Entity {

	protected Vector2 velocity;
	protected float speed;
	
	public MovableEntity(float speed, Vector2 position, float width, float height)
	{
		super(position, width, height);
		this.speed = speed;
		velocity = new Vector2(0,0);
	}

	public void update()
	{
		bounds.x = position.x;
		bounds.y = position.y;
	}
	
	public Vector2 getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
