package com.me.ninja_game_prototype.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.ninja_game_prototype.audio.GameAudio;
import com.me.ninja_game_prototype.helper.LevelLoader;
import com.me.ninja_game_prototype.model.GameModel;
import com.me.ninja_game_prototype.model.WorldModel;
import com.me.ninja_game_prototype.view.WorldView;

public class WorldController implements InputProcessor
{
	public WorldController()
	{
		Gdx.input.setInputProcessor(this);
		
		LevelLoader.get().loadLevel(0);
		WorldView.get().init();
	}

	Vector3 touch = new Vector3();
	Vector2 vect2Touch = new Vector2();
	Vector2 vect2Ninja = new Vector2();
	
	@Override
	public boolean keyDown(int keycode)
	{
		WorldModel.get().getNinja().setFlagInput(2);
		WorldModel.get().getNinja().setMoved(true);
		
		if(!GameModel.get().isGameEnd())
			GameAudio.walk();
		
		switch(keycode){
		case Keys.W:
			WorldModel.get().getNinja().entity.getVelocity().y = 1;
			break;
		case Keys.S:
			WorldModel.get().getNinja().entity.getVelocity().y = -1;
			break;
		case Keys.A:
			WorldModel.get().getNinja().entity.getVelocity().x = -1;
			break;
		case Keys.D:
			WorldModel.get().getNinja().entity.getVelocity().x = 1;
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		GameAudio.stopWalk();

		switch(keycode){
		case Keys.W:
			if(WorldModel.get().getNinja().entity.getVelocity().y == 1)
				WorldModel.get().getNinja().entity.getVelocity().y = 0;
			break;
		case Keys.S:
			if(WorldModel.get().getNinja().entity.getVelocity().y == -1)
				WorldModel.get().getNinja().entity.getVelocity().y = 0;
			break;
		case Keys.A:
			if(WorldModel.get().getNinja().entity.getVelocity().x == -1)
				WorldModel.get().getNinja().entity.getVelocity().x = 0;
			break;
		case Keys.D:
			if(WorldModel.get().getNinja().entity.getVelocity().x == 1)
				WorldModel.get().getNinja().entity.getVelocity().x = 0;
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if(!GameModel.get().isGameEnd()){
			GameAudio.walk();
			
			WorldModel.get().getNinja().setFlagInput(1);
			WorldModel.get().getNinja().setMoved(true);
			
			touch.set(screenX,screenY,0);
			WorldView.get().getCam().unproject(touch);
			vect2Touch.set(touch.x,touch.y);
			
			WorldModel.get().getNinja().setGoal(new Vector2(vect2Touch));
			
			vect2Touch.sub(WorldModel.get().getNinja().entity.getPosition()).nor();
			
			WorldModel.get().getNinja().entity.setVelocity(new Vector2(WorldModel.get().getNinja().entity.getPosition().cpy().scl(vect2Touch).nor()));
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}
