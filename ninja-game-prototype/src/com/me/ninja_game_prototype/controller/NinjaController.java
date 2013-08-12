package com.me.ninja_game_prototype.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.ninja_game_prototype.model.GameModel;
import com.me.ninja_game_prototype.model.WorldModel;
import com.me.ninja_game_prototype.view.WorldView;

public class NinjaController implements InputProcessor
{
	public NinjaController()
	{
	}

	static Sound pipeTune1 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/songs/pipe_tone1.wav"));
	static Sound pipeTune2 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/songs/pipe_tone2.wav"));
	static Sound pipeTune3 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/songs/pipe_tone3.wav"));
	static Sound pipeTune4 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/songs/pipe_tone4.wav"));
	
	static Sound pipeSong1 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/songs/pipe_song1.wav"));
	static Sound walk = Gdx.audio.newSound(Gdx.files.internal("data/sounds/walk_wood_normalspeed.mp3"));
	static Sound hit = Gdx.audio.newSound(Gdx.files.internal("data/sounds/hit_ouch.mp3"));
	
	Vector3 touch = new Vector3();
	Vector2 vect2Touch = new Vector2();
	
	@Override
	public boolean keyDown(int keycode)
	{
		if (!GameModel.get().isSongMode()){
			WorldModel.get().getNinja().setFlagInput(2);
			WorldModel.get().setNight(true);
		
			if(!GameModel.get().isGameEnd())
				NinjaController.walk();
			
			switch(keycode){
				case Keys.W:
					WorldModel.get().getNinja().getVelocity().y = 1;
					break;
				case Keys.S:
					WorldModel.get().getNinja().getVelocity().y = -1;
					break;
				case Keys.A:
					WorldModel.get().getNinja().getVelocity().x = -1;
					break;
				case Keys.D:
					WorldModel.get().getNinja().getVelocity().x = 1;
					break;
			}
		
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		NinjaController.stopWalk();

		if (keycode != Keys.O){
			switch(keycode){
				case Keys.W:
					if(WorldModel.get().getNinja().getVelocity().y == 1)
						WorldModel.get().getNinja().getVelocity().y = 0;
					break;
				case Keys.S:
					if(WorldModel.get().getNinja().getVelocity().y == -1)
						WorldModel.get().getNinja().getVelocity().y = 0;
					break;
				case Keys.A:
					if(WorldModel.get().getNinja().getVelocity().x == -1)
						WorldModel.get().getNinja().getVelocity().x = 0;
					break;
				case Keys.D:
					if(WorldModel.get().getNinja().getVelocity().x == 1)
						WorldModel.get().getNinja().getVelocity().x = 0;
					break;
			}
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
			NinjaController.walk();
			
			WorldModel.get().getNinja().setFlagInput(1);
			WorldModel.get().setNight(true);
			
			touch.set(screenX,screenY,0);
			WorldView.get().getCam().unproject(touch);
			vect2Touch.set(touch.x,touch.y);
			
			WorldModel.get().getNinja().setGoal(new Vector2(vect2Touch));
			
			vect2Touch.sub(WorldModel.get().getNinja().getPosition()).nor();
			
			WorldModel.get().getNinja().setVelocity(new Vector2(WorldModel.get().getNinja().getPosition().cpy().scl(vect2Touch).nor()));
		}
		return true;
	}
	
	public static void playPipeTune1(){
		pipeTune1.play();
	}
	public static void playPipeTune2(){
		pipeTune2.play();
	}
	public static void playPipeTune3(){
		pipeTune3.play();
	}
	public static void playPipeTune4(){
		pipeTune4.play();
	}
	public static void playPipeSong1(){
		pipeSong1.play();
	}
	
	public static void walk(){
		long id = walk.play(1.0f);
		walk.setLooping(id, true);
	}
	
	public static void stopWalk(){
		walk.stop();
	}
	
	public static void hitObstacle(){
		hit.play();
	}
	
	public static void stopHitObstacle(){
		hit.stop();
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
