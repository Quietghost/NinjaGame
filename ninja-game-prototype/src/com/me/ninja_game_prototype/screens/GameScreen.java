package com.me.ninja_game_prototype.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.controller.NinjaController;
import com.me.ninja_game_prototype.controller.SongController;
import com.me.ninja_game_prototype.controller.WorldController;
import com.me.ninja_game_prototype.helper.ConfigLoader;
import com.me.ninja_game_prototype.helper.SongLoader;
import com.me.ninja_game_prototype.model.GameModel;
import com.me.ninja_game_prototype.model.WorldModel;
import com.me.ninja_game_prototype.view.WorldView;

public class GameScreen implements Screen{

	NinjaGamePrototype game;
	
	public GameScreen (NinjaGamePrototype game){
		
		ConfigLoader.get().loadConfig();
		SongLoader.get().loadSongs(); // TODO: better in SongController
		
		this.game = game;
		new WorldController();
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(SongController.get()); // TODO: Singelton?
		multiplexer.addProcessor(new NinjaController());
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	@Override
	public void render(float delta)
	{
		if(!GameModel.get().isGameEnd()){
			WorldModel.get().update();
			WorldView.get().render();
		}else{
			game.setScreen(new EndScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		WorldModel.get().dispose();
		WorldView.get().dispose();
	}

}
