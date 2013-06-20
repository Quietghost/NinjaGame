package com.me.ninja_game_prototype.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.audio.GameAudio;
import com.me.ninja_game_prototype.view.tween.SpriteTween;

public class SplashScreen implements Screen{

	Texture splashTexture;
	Sprite splashSprite;
	SpriteBatch batch;
	NinjaGamePrototype game;
	TweenManager manager;
	BitmapFont font;
	CharSequence str1 = "The 36 Chambers of the Panpipe Ninjas";
	CharSequence str2 = "Designed and developed by: Sebastian Matyas, Christian Matyas, Steve Seypt and Jing Wang";
	
	public SplashScreen(NinjaGamePrototype game){
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		manager.update(delta);
		
		batch.begin();
		splashSprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		GameAudio.playMusic(true);
		splashTexture = new Texture("data/splashScreen.png");
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		splashSprite = new Sprite(splashTexture);
		splashSprite.setColor(1, 1, 1, 0);
		//splashSprite.setScale(0.8f);
		splashSprite.setX(Gdx.graphics.getWidth()/2 - (splashSprite.getWidth()/2));
		splashSprite.setY(Gdx.graphics.getHeight()/2 - (splashSprite.getHeight()/2));
		
		batch = new SpriteBatch();
		
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		
		manager = new TweenManager();
		
		TweenCallback cb = new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenCompleted();
			}
		};
		
		Tween.to(splashSprite, SpriteTween.ALPHA, 4f).target(1)
		.ease(TweenEquations.easeInQuad).repeatYoyo(1, 2f).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
	}

	
	private void tweenCompleted() {
		if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "finished");
		game.setScreen(new GameScreen(game));
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
		
	}

}
