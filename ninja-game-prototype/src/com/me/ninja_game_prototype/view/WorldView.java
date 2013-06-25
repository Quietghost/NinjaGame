package com.me.ninja_game_prototype.view;

import java.io.IOException;
import java.util.List;

import box2dLight.PointLight;
import box2dLight.RayHandler;

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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.helper.ShaderSettings;
import com.me.ninja_game_prototype.model.ExitModel;
import com.me.ninja_game_prototype.model.GameModel;
import com.me.ninja_game_prototype.model.NinjaModel;
import com.me.ninja_game_prototype.model.ObstacleModel;
import com.me.ninja_game_prototype.model.WorldModel;

public class WorldView
{
	/* static */
	
	/* singleton */
	private static WorldView instance;

	private WorldView() {}

	public static WorldView get()
	{
		if (WorldView.instance == null)
		{
			WorldView.instance = new WorldView();
		}
		return WorldView.instance;
	}
	
	/* instance */
	SpriteBatch batch;
	SpriteBatch menu;
	OrthographicCamera cam;
	Texture mapTexture;
	Texture ninjaTexture;
	Texture ninjaTexture_dark;
	Texture panpipe;
	float width, height;
	ShapeRenderer shaperenderer;
	ParticleEmitter hit;
	World box2dworld;
	ShaderProgram shader;
	Box2DDebugRenderer box2drenderer;
	RayHandler rayhandler;
	PointLight p;
	float fadeTimeAlpha = 0;
	
	Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/hit_ouch.mp3"));

	public void init()
	{
		// TODO replace with non debug
		box2drenderer = new Box2DDebugRenderer();
		box2dworld = new World(new Vector2(0,  -9.8f), false);
		
		width = (Gdx.graphics.getWidth());
		height = (Gdx.graphics.getHeight());
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		mapTexture = new Texture("data/map.png");
		ninjaTexture = new Texture("data/ninja.png");
		
		ninjaTexture_dark = new Texture("data/eyes.png");
		ninjaTexture_dark.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		panpipe = new Texture("data/panpipe2.png");
		panpipe.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(ShaderSettings.VERT, ShaderSettings.FRAG);
		
		batch = new SpriteBatch(1000, shader);
		batch.setShader(shader);
		batch.setProjectionMatrix(cam.combined);
		
		menu = new SpriteBatch();
		menu.setProjectionMatrix(cam.combined);
		
		shader.begin();
		shader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shader.end();
		
		shaperenderer = new ShapeRenderer();
		
		hit = new ParticleEmitter();
		
		try
		{
			hit.load(Gdx.files.internal("data/particle").reader(5000));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		Texture hitTexture = new Texture(Gdx.files.internal("data/particle.png"));
		Sprite sparkle = new Sprite(hitTexture);
		hit.setSprite(sparkle);
		hit.getScale().setHigh(10);
		hit.start();
		
		RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);
        
		rayhandler = new RayHandler(box2dworld);
		rayhandler.setAmbientLight(0.2f, 0.2f, 0.2f, 0.1f);
		rayhandler.setBlurNum(1);
		rayhandler.setCombinedMatrix(cam.combined);
		
		p = new PointLight(rayhandler, 1000, Color.WHITE, 700, 0, height);
		
		for (ObstacleModel obstacle : WorldModel.get().getObstacles())
		{
			BodyDef bodyDef = new BodyDef();  
			bodyDef.position.set(
				new Vector2(
					obstacle.getPosition().x + (obstacle.getBounds().width/2),
					obstacle.getPosition().y + (obstacle.getBounds().height/2)
				)
			);  
			
			Body body = box2dworld.createBody(bodyDef);
			body.setUserData(obstacle);

			PolygonShape box = new PolygonShape();  
			box.setAsBox(obstacle.getBounds().width/2, obstacle.getBounds().height/2);
			body.createFixture(box, 0.0f); 
			box.dispose();
		}
	}
	
	public OrthographicCamera getCam()
	{
		return cam;
	}

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
		
		if(!WorldModel.get().isNight()){
			box2drenderer.render(box2dworld, cam.combined);
			box2dworld.step(1/60f, 6, 2);
		}
		
		batch.begin();
		if(!WorldModel.get().isNight())
		{
			batch.draw(mapTexture, 0, 0);
			batch.draw(ninjaTexture, ninja.getPosition().x, ninja.getPosition().y, ninja.getWidth(), ninja.getHeight());
			for (ObstacleModel obstacle : obstacles)
			{
				batch.draw(obstacle.getTexture(), obstacle.getPosition().x, obstacle.getPosition().y, obstacle.getWidth(), obstacle.getHeight());
			}
		}
		else
		{
			batch.draw(ninjaTexture_dark, ninja.getPosition().x + ninjaTexture_dark.getWidth()/2, ninja.getPosition().y + ninjaTexture_dark.getHeight());
		}
		
		for (ObstacleModel obstacle : obstacles)
		{
			if(obstacle.isRumble())
			{
				hit.setPosition(obstacle.getPosition().x + obstacle.getWidth()/2, obstacle.getPosition().y + obstacle.getHeight()/2);
				hit.draw(batch, Gdx.graphics.getDeltaTime());
			}
		}

		batch.end();
		
		if (NinjaGamePrototype.DEBUG)
		{
			shaperenderer.setProjectionMatrix(cam.combined);
			shaperenderer.begin(ShapeType.Line);
			shaperenderer.setColor(Color.ORANGE);
			shaperenderer.rect(ninja.getBounds().x, ninja.getBounds().y, ninja.getBounds().width, ninja.getBounds().height);
			shaperenderer.setColor(Color.RED);
			for (ObstacleModel obstacle : obstacles)
			{
				shaperenderer.rect(obstacle.getBounds().x, obstacle.getBounds().y, obstacle.getBounds().width, obstacle.getBounds().height);
			}
			shaperenderer.rect(exit.getBounds().x, exit.getBounds().y, exit.getBounds().width, exit.getBounds().height);
			shaperenderer.end();
		}
		
		if(!WorldModel.get().isNight())
		{
			rayhandler.updateAndRender();
		}
		
		if(GameModel.get().isSongModeShow())
		{
			menu.begin();
			fadeTimeAlpha = fadeTimeAlpha + Gdx.graphics.getDeltaTime();
			if (fadeTimeAlpha >= 1.0f){
				fadeTimeAlpha = 1.0f;
			}
			menu.setColor(1.0f, 1.0f, 1.0f, fadeTimeAlpha);
			menu.draw(panpipe, Gdx.graphics.getWidth()/2 - panpipe.getWidth()/2, 
					Gdx.graphics.getHeight()/2 - panpipe.getHeight()/2);
			menu.end();
		}
		
		if(GameModel.get().isSongModeHide())
		{
			menu.begin();
			fadeTimeAlpha = fadeTimeAlpha - Gdx.graphics.getDeltaTime();
			if (fadeTimeAlpha <= 0.0f){
				fadeTimeAlpha = 0.0f;
			}
			menu.setColor(1.0f, 1.0f, 1.0f, fadeTimeAlpha);
			menu.draw(panpipe, Gdx.graphics.getWidth()/2 - panpipe.getWidth()/2, 
					Gdx.graphics.getHeight()/2 - panpipe.getHeight()/2);
			menu.end();
		}
		
	}
	
	public void dispose()
	{
		batch.dispose();
		ninjaTexture.dispose();
//		obstacleTexture1.dispose();
//		obstacleTexture2.dispose();
		ninjaTexture_dark.dispose();
	}
}
