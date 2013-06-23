package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.view.Audio;

public class Ninja extends MovableEntity{
	
	Vector2 goal = new Vector2();
	int flagInput;
	boolean moved;
		
	public Ninja(float SPEED, float width, float height, Vector2 position) {
		super(SPEED, width, height, position);
	}

	public void update() {
		
		switch (flagInput){
		
			case 1:
				if (!this.bounds.contains(this.getGoal()) && this.position.dst(this.goal) > 5){		
					position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime() * SPEED));
				}else{
					this.setPosition(new Vector2(goal.x - this.bounds.width/2, goal.y - this.bounds.height/2));
					this.setVelocity(new Vector2(0, 0));
					Audio.stopWalk();
				}
				
				bounds.x = position.x;
				bounds.y = position.y;
				break;
			case 2:
					
				position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime() * SPEED));
				
				bounds.x = position.x;
				bounds.y = position.y;
				break;
		}
		
	}


	/**
	 * @return the goal
	 */
	public Vector2 getGoal() {
		return goal;
	}


	/**
	 * @param goal the goal to set
	 */
	public void setGoal(Vector2 goal) {
		
		this.goal = goal;
	}


	/**
	 * @return the flagInput
	 */
	public int getFlagInput() {
		return flagInput;
	}


	/**
	 * @param flagInput the flagInput to set
	 */
	public void setFlagInput(int flagInput) {
		this.flagInput = flagInput;
	}

	/**
	 * @return the moved
	 */
	public boolean isMoved() {
		return moved;
	}

	/**
	 * @param moved the moved to set
	 */
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

}
