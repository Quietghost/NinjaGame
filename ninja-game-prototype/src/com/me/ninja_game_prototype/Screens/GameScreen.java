package com.me.ninja_game_prototype.Screens;

import com.badlogic.gdx.Screen;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.View.World;
import com.me.ninja_game_prototype.View.WorldRenderer;

public class GameScreen implements Screen{

	NinjaGamePrototype game;
	World world;
	WorldRenderer render;
	
	public GameScreen (NinjaGamePrototype game){
		this.game = game;
		world = new World(game);
		render = new WorldRenderer(world);
	}
	
	
	@Override
	public void render(float delta) {
		if(!world.isGameEnd()){
			world.update();
			render.render();
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
		world.dispose();
		render.dispose();
	}

}
