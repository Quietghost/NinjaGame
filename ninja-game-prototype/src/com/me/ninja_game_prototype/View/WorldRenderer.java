package com.me.ninja_game_prototype.View;

import java.io.IOException;

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
import com.me.ninja_game_prototype.Model.Exit;
import com.me.ninja_game_prototype.Model.Ninja;
import com.me.ninja_game_prototype.Model.Obstacle;

public class WorldRenderer {
	
	World world;
	SpriteBatch batch;
	Ninja ninja;
	OrthographicCamera cam;
	Texture mapTexture;
	Texture ninjaTexture;
	Texture ninjaTexture_dark;
	Texture obstacleTexture1;
	Texture obstacleTexture2;
	float width, height;
	ShapeRenderer shaperenderer;
	Obstacle obstacle1;
	Obstacle obstacle2;
	Exit exit;
	ParticleEmitter hit;
	
	Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/hit_ouch.mp3"));

	public WorldRenderer(World world){
		this.world = world;
		
		world.setWorldrenderer(this);
		
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
	public void setCam(OrthographicCamera cam) {
		this.cam = cam;
	}

	public void render(){
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		ninja = world.getNinja();
		obstacle1 = world.getObstacle1();
		obstacle2 = world.getObstacle2();
		exit = world.getExit();
		
		batch.begin();
		if(!ninja.isMoved()){
			batch.draw(mapTexture, 0, 0);
			batch.draw(ninjaTexture, ninja.getPosition().x, ninja.getPosition().y, ninja.getWidth(), ninja.getHeight());
			batch.draw(obstacleTexture1, obstacle1.getPosition().x, obstacle1.getPosition().y, obstacle1.getWidth(), obstacle1.getHeight());
			batch.draw(obstacleTexture2, obstacle2.getPosition().x, obstacle2.getPosition().y, obstacle2.getWidth(), obstacle2.getHeight());
			
		}else{
			batch.draw(ninjaTexture_dark, ninja.getPosition().x + ninjaTexture_dark.getWidth()/2, ninja.getPosition().y + ninjaTexture_dark.getHeight());
		}
		
		if(obstacle1.isRumble()){
			hit.setPosition(obstacle1.getPosition().x + obstacle1.getWidth()/2, obstacle1.getPosition().y + obstacle1.getHeight()/2);
			hit.draw(batch, Gdx.graphics.getDeltaTime());
		}
		
		if(obstacle2.isRumble()){
			hit.setPosition(obstacle2.getPosition().x + obstacle2.getWidth()/2, obstacle2.getPosition().y + obstacle2.getHeight()/2);
			hit.draw(batch, Gdx.graphics.getDeltaTime());
		}

		batch.end();
		
		if (NinjaGamePrototype.DEBUG){
			shaperenderer.setProjectionMatrix(cam.combined);
			shaperenderer.begin(ShapeType.Line);
			shaperenderer.setColor(Color.ORANGE);
			shaperenderer.rect(ninja.getBounds().x, ninja.getBounds().y, ninja.getBounds().width, ninja.getBounds().height);
			shaperenderer.setColor(Color.RED);
			shaperenderer.rect(obstacle1.getBounds().x, obstacle1.getBounds().y, obstacle1.getBounds().width, obstacle1.getBounds().height);
			shaperenderer.rect(obstacle2.getBounds().x, obstacle2.getBounds().y, obstacle2.getBounds().width, obstacle2.getBounds().height);
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
