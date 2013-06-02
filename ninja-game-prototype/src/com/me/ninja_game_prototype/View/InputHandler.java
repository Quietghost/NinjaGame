package com.me.ninja_game_prototype.View;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.ninja_game_prototype.Model.Ninja;

public class InputHandler implements InputProcessor{

	World world;
	Ninja ninja;
	Vector3 touch = new Vector3();
	Vector2 vect2Touch = new Vector2();
	Vector2 vect2Ninja = new Vector2();
	
	public InputHandler(World world){
		this.world = world;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		ninja = world.getNinja();
		ninja.setFlagInput(2);
		ninja.setMoved(true);
		
		if(!world.isGameEnd())
			Audio.walk();
		
		switch(keycode){
		case Keys.W:
			ninja.getVelocity().y = 1;
			break;
		case Keys.S:
			ninja.getVelocity().y = -1;
			break;
		case Keys.A:
			ninja.getVelocity().x = -1;
			break;
		case Keys.D:
			ninja.getVelocity().x = 1;
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		ninja = world.getNinja();
		
		Audio.stopWalk();

		switch(keycode){
		case Keys.W:
			if(ninja.getVelocity().y == 1)
			ninja.getVelocity().y = 0;
			break;
		case Keys.S:
			if(ninja.getVelocity().y == -1)
			ninja.getVelocity().y = 0;
			break;
		case Keys.A:
			if(ninja.getVelocity().x == -1)
			ninja.getVelocity().x = 0;
			break;
		case Keys.D:
			if(ninja.getVelocity().x == 1)
			ninja.getVelocity().x = 0;
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(!world.isGameEnd()){
			Audio.walk();
			
			ninja = world.getNinja();
			ninja.setFlagInput(1);
			ninja.setMoved(true);
			
			touch.set(screenX,screenY,0);
			world.getWorldrenderer().getCam().unproject(touch);
			vect2Touch.set(touch.x,touch.y);
			
			ninja.setGoal(new Vector2(vect2Touch));
			
			vect2Touch.sub(ninja.getPosition()).nor();
			
			ninja.setVelocity(new Vector2(ninja.getPosition().cpy().scl(vect2Touch).nor()));
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
