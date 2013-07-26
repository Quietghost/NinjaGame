package com.me.ninja_game_prototype.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
import com.badlogic.gdx.utils.Array;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.controller.ConfigController;
import com.me.ninja_game_prototype.controller.SongController;
import com.me.ninja_game_prototype.helper.ShaderSettings;
import com.me.ninja_game_prototype.model.EnemyModel;
import com.me.ninja_game_prototype.model.ExitModel;
import com.me.ninja_game_prototype.model.FloorModel;
import com.me.ninja_game_prototype.model.GameModel;
import com.me.ninja_game_prototype.model.NinjaModel;
import com.me.ninja_game_prototype.model.ObstacleModel;
import com.me.ninja_game_prototype.model.SongModel;
import com.me.ninja_game_prototype.model.WallModel;
import com.me.ninja_game_prototype.model.WorldModel;

public class WorldViewLight
{
	/* static */
	
	/* singleton */
	private static WorldViewLight instance;

	private WorldViewLight() {}

	public static WorldViewLight get()
	{
		if (WorldViewLight.instance == null)
		{
			WorldViewLight.instance = new WorldViewLight();
		}
		return WorldViewLight.instance;
	}
	
	/* instance */
	SpriteBatch batch;
	OrthographicCamera cam;
	float width, height;
	ShapeRenderer shaperenderer;
	World box2dworld;
	ShaderProgram shader;
	Box2DDebugRenderer box2drenderer;
	RayHandler rayhandler;
	PointLight p;
	TextureRegion frame;
	
	OrthogonalTiledMapRenderer mapRenderer;
	
	public void init()
	{
		box2drenderer = new Box2DDebugRenderer(); // TODO non debug?
		box2dworld = new World(new Vector2(0,  -9.8f), false);
		
		width = (Gdx.graphics.getWidth());
		height = (Gdx.graphics.getHeight());
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(ShaderSettings.VERT, ShaderSettings.FRAG);
		
		mapRenderer = new OrthogonalTiledMapRenderer(WorldModel.get().getMap());
		mapRenderer.setView(cam);
		
		batch = mapRenderer.getSpriteBatch();
		batch.setShader(shader);
		batch.setProjectionMatrix(cam.combined);
		
		shader.begin();
		shader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shader.end();
		
		shaperenderer = new ShapeRenderer();
			
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
		
		frame = ninja.getIdleLight();
		batch.draw(frame, ninja.getPosition().x, ninja.getPosition().y);
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
		
		p = new PointLight(rayhandler, 1000, Color.WHITE, 700, 0, height);
		rayhandler.setAmbientLight(0.2f, 0.2f, 0.2f, 0.1f);
		
		rayhandler.updateAndRender();
		
		
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
