package com.me.ninja_game_prototype.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.audio.GameAudio;
import com.me.ninja_game_prototype.model.GameModel;

public class EndScreen implements Screen
{
	NinjaGamePrototype game;
	BitmapFont font1;
	BitmapFont font2;
	BitmapFont font3;
	SpriteBatch batch;
	CharSequence str1 = "The End ... ";
	CharSequence str2 = "Design, Programming (and Motivation) by Sebastian, Christian, Steve and Jing";
	CharSequence str3;
	
	
	public EndScreen(NinjaGamePrototype game){
		GameAudio.stopWalk();
		this.game = game;
		batch = new SpriteBatch();
		font1 = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		font2 = new BitmapFont(Gdx.files.internal("data/font1.fnt"), false);
		font3 = new BitmapFont(Gdx.files.internal("data/font2.fnt"), false);
		str3 = "Failed attempts: " + GameModel.get().getAttempts();
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		font1.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font1.draw(batch, str1, Gdx.graphics.getWidth() / 2 - str1.length()*10, Gdx.graphics.getHeight() / 2);
		font3.draw(batch, str3, Gdx.graphics.getWidth() / 2 - str1.length()*10, 
				Gdx.graphics.getHeight() / 2 - font1.getCapHeight() - font1.getLineHeight());
		font2.draw(batch, str2, 20, Gdx.graphics.getHeight() / 20);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		font1.dispose();
		font2.dispose();
	}
}
