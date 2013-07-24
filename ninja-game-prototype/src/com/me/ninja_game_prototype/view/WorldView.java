package com.me.ninja_game_prototype.view;

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
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.controller.SongController;
import com.me.ninja_game_prototype.helper.ShaderSettings;
import com.me.ninja_game_prototype.model.EnemyModel;
import com.me.ninja_game_prototype.model.ExitModel;
import com.me.ninja_game_prototype.model.FloorModel;
import com.me.ninja_game_prototype.model.NinjaModel;
import com.me.ninja_game_prototype.model.ObstacleModel;
import com.me.ninja_game_prototype.model.WallModel;
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
	SpriteBatch menuBatch;
	OrthographicCamera cam;
	Texture panpipe;
	float width, height;
	ShapeRenderer shaperenderer;
	ParticleEffect hit;
	ParticleEffect toneEffect1;
	ParticleEffect toneEffect2;
	ParticleEffect toneEffect3;
	ParticleEffect toneEffect4;
	World box2dworld;
	ShaderProgram shader;
	Box2DDebugRenderer box2drenderer;
	RayHandler rayhandler;
	PointLight p;
	float fadeTimeAlpha = 0;
	
	OrthogonalTiledMapRenderer mapRenderer;
	
	Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/hit_ouch.mp3"));

	public void init()
	{
		box2drenderer = new Box2DDebugRenderer(); // TODO non debug?
		box2dworld = new World(new Vector2(0,  -9.8f), false);
		
		width = (Gdx.graphics.getWidth());
		height = (Gdx.graphics.getHeight());
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		// TODO do wee need this?
		WorldModel.get().getNinja().getNightTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		panpipe = new Texture("data/panpipe2.png");
		panpipe.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(ShaderSettings.VERT, ShaderSettings.FRAG);
		
		mapRenderer = new OrthogonalTiledMapRenderer(WorldModel.get().getMap());
		mapRenderer.setView(cam);
		
		batch = mapRenderer.getSpriteBatch();
		batch.setShader(shader);
		batch.setProjectionMatrix(cam.combined);
		
		menuBatch = new SpriteBatch();
		menuBatch.setProjectionMatrix(cam.combined);
		
		shader.begin();
		shader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shader.end();
		
		shaperenderer = new ShapeRenderer();
		
		hit = new ParticleEffect();
		hit.load(Gdx.files.internal("data/particle"), Gdx.files.internal("data"));
		hit.start();
		
		toneEffect1 = new ParticleEffect();
		toneEffect1.load(Gdx.files.internal("data/songs/tone_new"), Gdx.files.internal("data/songs"));
		toneEffect1.start();
		
		toneEffect2 = new ParticleEffect();
		toneEffect2.load(Gdx.files.internal("data/songs/tone_new"), Gdx.files.internal("data/songs"));
		toneEffect2.start();
		
		toneEffect3 = new ParticleEffect();
		toneEffect3.load(Gdx.files.internal("data/songs/tone_new"), Gdx.files.internal("data/songs"));
		toneEffect3.start();
		
		toneEffect4 = new ParticleEffect();
		toneEffect4.load(Gdx.files.internal("data/songs/tone_new"), Gdx.files.internal("data/songs"));
		toneEffect4.start();
		
		RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);
        
		rayhandler = new RayHandler(box2dworld);
		rayhandler.setBlurNum(1);
		rayhandler.setCombinedMatrix(cam.combined);
		
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
		
		List<ObstacleModel> obstacles = WorldModel.get().getObstacles();
		List<EnemyModel> enemies = WorldModel.get().getEnemies();
		NinjaModel ninja = WorldModel.get().getNinja();
		
		FloorModel floor = WorldModel.get().getFloor();
		WallModel wall = WorldModel.get().getWall();
		ExitModel exit = WorldModel.get().getExit();
		
		if(!WorldModel.get().isNight())
		{
			box2drenderer.render(box2dworld, cam.combined);
			box2dworld.step(1/60f, 6, 2);
		}
		
		batch.begin();

		mapRenderer.renderTileLayer(floor.getLayer());
		mapRenderer.renderTileLayer(wall.getLayer());
		mapRenderer.renderTileLayer(exit.getLayer());
		
		for (EnemyModel enemy: enemies)
			batch.draw(enemy.getTexture(), enemy.getPosition().x, enemy.getPosition().y);
		
		for (ObstacleModel obstacle : obstacles)
			batch.draw(obstacle.getTexture(), obstacle.getPosition().x, obstacle.getPosition().y);
		
		if(!WorldModel.get().isNight())
			batch.draw(ninja.getTexture(), ninja.getPosition().x, ninja.getPosition().y);
		else
		{
			
			TextureRegion frame = ninja.getIdle();
			if(ninja.getVelocity().x != 0 || ninja.getVelocity().y != 0)
			{
				frame = ninja.getWalk();
			}
			batch.draw(frame, ninja.getPosition().x, ninja.getPosition().y);
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
		
		if (NinjaGamePrototype.SHOW_BOUNDINGBOXES || NinjaGamePrototype.SHOW_ENEMYPATHS)
		{
			shaperenderer.setProjectionMatrix(cam.combined);
			shaperenderer.begin(ShapeType.Line);
			
			if (NinjaGamePrototype.SHOW_BOUNDINGBOXES)
			{
				shaperenderer.setColor(Color.ORANGE);
				shaperenderer.rect(ninja.getBounds().x, ninja.getBounds().y, ninja.getBounds().width, ninja.getBounds().height);
				shaperenderer.setColor(Color.RED);
				for (ObstacleModel obstacle : obstacles)
				{
					shaperenderer.rect(obstacle.getBounds().x, obstacle.getBounds().y, obstacle.getBounds().width, obstacle.getBounds().height);
				}
				
	// TODO			shaperenderer.rect(exit.getBounds().x, exit.getBounds().y, exit.getBounds().width, exit.getBounds().height);
			}
			
			if (NinjaGamePrototype.SHOW_ENEMYPATHS)
			{
				for (EnemyModel enemy: enemies)
				{
				
					if (enemy.getTransformedVertices().length>0)
					{
						shaperenderer.setColor(Color.GREEN);
						shaperenderer.polygon(enemy.getTransformedVertices());
					}
				}
			}
			
			shaperenderer.end();
		}

		
		rayhandler.removeAll();
		if(!WorldModel.get().isNight())
		{
			p = new PointLight(rayhandler, 1000, Color.WHITE, 700, 0, height);
			rayhandler.setAmbientLight(0.2f, 0.2f, 0.2f, 0.1f);
		}
		else
		{
			rayhandler.setAmbientLight(0, 0, 0, 0);
			for (EnemyModel enemy: enemies)
			{
				p = new PointLight(rayhandler, 50, new Color(255, 255, 255, 0.2f), 300, enemy.getCenter().x, enemy.getCenter().y);
			}
			p = new PointLight(rayhandler, 5, new Color(255, 255, 255, 1), ninja.getWidth(), ninja.getCenter().x, ninja.getCenter().y);
		}
		rayhandler.updateAndRender();
		
		batch.begin();
		if(SongController.get().getTonePlayed() != ""){
			
			switch (SongController.get().getTonePlayed()){
			
			case "X":
				toneEffect1.setPosition(ninja.getPosition().x + ninja.getWidth(), ninja.getPosition().y + ninja.getHeight());
				toneEffect1.draw(batch, Gdx.graphics.getDeltaTime());
				break;
			case "Y":
				toneEffect2.setPosition(ninja.getPosition().x + ninja.getWidth(), ninja.getPosition().y + ninja.getHeight());
				toneEffect2.draw(batch, Gdx.graphics.getDeltaTime());
				break;
			case "A":
				toneEffect3.setPosition(ninja.getPosition().x + ninja.getWidth(), ninja.getPosition().y + ninja.getHeight());
				toneEffect3.draw(batch, Gdx.graphics.getDeltaTime());
				break;
			case "B":
				toneEffect4.setPosition(ninja.getPosition().x + ninja.getWidth(), ninja.getPosition().y + ninja.getHeight());
				toneEffect4.draw(batch, Gdx.graphics.getDeltaTime());
				break;
				
			
			}
		}
		batch.end();
		
		if(toneEffect1.isComplete()) {
			SongController.get().setTonePlayed("");
			toneEffect1.reset();
		}
		
		if(toneEffect2.isComplete()) {
			SongController.get().setTonePlayed("");
			toneEffect2.reset();
		}
		
		if(toneEffect3.isComplete()) {
			SongController.get().setTonePlayed("");
			toneEffect3.reset();
		}
		
		if(toneEffect4.isComplete()) {
			SongController.get().setTonePlayed("");
			toneEffect4.reset();
		}
		
	}
	
	public void dispose()
	{
		batch.dispose();
//		ninjaTexture.dispose();
//		obstacleTexture1.dispose();
//		obstacleTexture2.dispose();
//		ninjaTexture_dark.dispose();
	}
}
