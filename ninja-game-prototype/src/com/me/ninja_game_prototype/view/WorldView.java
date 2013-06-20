package com.me.ninja_game_prototype.view;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.model.ExitModel;
import com.me.ninja_game_prototype.model.NinjaModel;
import com.me.ninja_game_prototype.model.ObstacleModel;
import com.me.ninja_game_prototype.model.WorldModel;

public class WorldView
{
	/* static */
	
	/* singleton */
	private static WorldView instance;

	private WorldView() {
	}

	public static WorldView get() {
		if (WorldView.instance == null) {
			WorldView.instance = new WorldView();
		}
		return WorldView.instance;
	}
	
	/* instance */
	SpriteBatch batch;
	OrthographicCamera cam;
	Texture mapTexture;
	Texture ninjaTexture;
	Texture ninjaTexture_dark;
	Texture obstacleTexture1;
	Texture obstacleTexture2;
	float width, height;
	ShapeRenderer shaperenderer;
	ParticleEmitter hit;
	
	Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/hit_ouch.mp3"));

	public void init()
	{
		width = (Gdx.graphics.getWidth());
		height = (Gdx.graphics.getHeight());
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		mapTexture = new Texture("data/map.png");
		//mapTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		ninjaTexture = new Texture("data/ninja.png");
		//ninjaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		ninjaTexture_dark = new Texture("data/eyes.png");
		ninjaTexture_dark.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		obstacleTexture1 = new Texture("data/obstacle_1.png");
		//obstacleTexture1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		obstacleTexture2 = new Texture("data/obstacle_2.png");
		//obstacleTexture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		shaperenderer = new ShapeRenderer();
		
		hit = new ParticleEmitter();
		
		try{
			hit.load(Gdx.files.internal("data/particle").reader(5000));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Texture hitTexture = new Texture(Gdx.files.internal("data/particle.png"));
		Sprite sparkle = new Sprite(hitTexture);
		hit.setSprite(sparkle);
		hit.getScale().setHigh(10);
		hit.start();
	}
	
	/**
	 * @return the cam
	 */
	public OrthographicCamera getCam() {
		return cam;
	}

	/**
	 * @param cam the cam to set
	 */
	public void setCam(OrthographicCamera cam)
	{
		this.cam = cam;
	}

	public void render()
	{
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		NinjaModel ninja = WorldModel.get().getNinja();
		ExitModel exit = WorldModel.get().getExit();
		List<ObstacleModel> obstacles = WorldModel.get().getObstacles();
		
		batch.begin();
		if(!ninja.isMoved())
		{
			batch.draw(mapTexture, 0, 0);
			batch.draw(ninjaTexture, ninja.entity.getPosition().x, ninja.entity.getPosition().y, ninja.entity.getWidth(), ninja.entity.getHeight());
			for (ObstacleModel obstacle : obstacles)
			{
				batch.draw(obstacleTexture1, obstacle.getPosition().x, obstacle.getPosition().y, obstacle.getWidth(), obstacle.getHeight());
			}
		}else{
			batch.draw(ninjaTexture_dark, ninja.entity.getPosition().x + ninjaTexture_dark.getWidth()/2, ninja.entity.getPosition().y + ninjaTexture_dark.getHeight());
		}
		
		for (ObstacleModel obstacle : obstacles) {
			if(obstacle.isRumble()){
				hit.setPosition(obstacle.getPosition().x + obstacle.getWidth()/2, obstacle.getPosition().y + obstacle.getHeight()/2);
				hit.draw(batch, Gdx.graphics.getDeltaTime());
			}
		}

		batch.end();
		
		if (NinjaGamePrototype.DEBUG){
			shaperenderer.setProjectionMatrix(cam.combined);
			shaperenderer.begin(ShapeType.Line);
			shaperenderer.setColor(Color.ORANGE);
			shaperenderer.rect(ninja.entity.getBounds().x, ninja.entity.getBounds().y, ninja.entity.getBounds().width, ninja.entity.getBounds().height);
			shaperenderer.setColor(Color.RED);
			for (ObstacleModel obstacle : obstacles) {
				shaperenderer.rect(obstacle.getBounds().x, obstacle.getBounds().y, obstacle.getBounds().width, obstacle.getBounds().height);
			}
			shaperenderer.rect(exit.getBounds().x, exit.getBounds().y, exit.getBounds().width, exit.getBounds().height);
			shaperenderer.end();
		}
	}
	
	public void dispose(){
		batch.dispose();
		ninjaTexture.dispose();
		obstacleTexture1.dispose();
		obstacleTexture2.dispose();
		ninjaTexture_dark.dispose();
	}
}
