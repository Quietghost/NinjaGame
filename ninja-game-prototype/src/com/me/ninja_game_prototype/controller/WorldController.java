package com.me.ninja_game_prototype.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.audio.GameAudio;
import com.me.ninja_game_prototype.helper.LevelLoader;
import com.me.ninja_game_prototype.helper.SongLoader;
import com.me.ninja_game_prototype.model.GameModel;
import com.me.ninja_game_prototype.model.SongRecorder;
import com.me.ninja_game_prototype.model.WorldModel;
import com.me.ninja_game_prototype.view.WorldView;

public class WorldController implements InputProcessor
{
	public WorldController()
	{
		Gdx.input.setInputProcessor(this);
		
		LevelLoader.get().loadLevel(0);
		SongLoader.get().loadSongs();
		WorldView.get().init();
	}

	Vector3 touch = new Vector3();
	Vector2 vect2Touch = new Vector2();
	Vector2 vect2Ninja = new Vector2();
	
	@Override
	public boolean keyDown(int keycode)
	{
		if (!GameModel.get().isSongModeShow()){
			WorldModel.get().getNinja().setFlagInput(2);
			WorldModel.get().setNight(true);
		
			if(!GameModel.get().isGameEnd())
				GameAudio.walk();
			
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
				case Keys.O:
					if (GameModel.get().isSongModeShow()){
						GameModel.get().setSongModeHide(true);
						GameModel.get().setSongModeShow(false);
					}else{
						GameModel.get().setSongModeHide(false);
						GameModel.get().setSongModeShow(true);
					}
					break;
			}
		}else{
			SongController.get().setSongToRecord("song_blind_guards");
			switch(keycode){
				case Keys.NUM_1:
					if(!SongRecorder.get().isRecorded())
						SongRecorder.get().getRecordedSong().add("1");
					break;
				case Keys.NUM_2:
					if(!SongRecorder.get().isRecorded())
						SongRecorder.get().getRecordedSong().add("2");
					break;
				case Keys.NUM_3:
					if(!SongRecorder.get().isRecorded())
						SongRecorder.get().getRecordedSong().add("3");
					break;
				case Keys.NUM_4:
					if(!SongRecorder.get().isRecorded())
						SongRecorder.get().getRecordedSong().add("4");
					break;
				case Keys.O:
					if (GameModel.get().isSongModeShow()){
						GameModel.get().setSongModeHide(true);
						GameModel.get().setSongModeShow(false);
					}else{
						GameModel.get().setSongModeHide(false);
						GameModel.get().setSongModeShow(true);
					}
					break;
			}
			
			if(SongRecorder.get().isRecorded()){
				if(SongController.get().validateSong(SongRecorder.get().getRecordedSong())){
					Gdx.app.log("SongController", "success");
				}else{
					Gdx.app.log("SongController", "failed");
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		GameAudio.stopWalk();

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
			GameAudio.walk();
			
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
