package com.me.ninja_game_prototype.Model;

import com.badlogic.gdx.math.Vector2;

public abstract class MovableEntity extends Entity {

	protected Vector2 velocity;
	protected float SPEED;
	
	public MovableEntity(float SPEED, float width, float height, Vector2 position){
		super(position, width, height);
		this.SPEED = SPEED;
		velocity = new Vector2(0,0);
	}

	public void update(Ninja ninja){
		bounds.x = position.x;
		bounds.y = position.y;
	}
	
	/**
	 * @return the velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

}
