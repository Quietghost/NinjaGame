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

public class WorldView
{
	
	/* instance */
	SpriteBatch batch;
	OrthographicCamera cam;
	float width, height;
	ShapeRenderer shaperenderer;
	ParticleEffect hit;
	ParticleEffect toneEffect;
	List<ParticleEffect> toneEffects = new ArrayList<ParticleEffect>();
	World box2dworld;
	ShaderProgram shader;
	Box2DDebugRenderer box2drenderer;
	RayHandler rayhandler;
	PointLight p;
	float fadeTimeAlpha = 0;
	int index = 0;
	TextureRegion frame;
	
	OrthogonalTiledMapRenderer mapRenderer;
	
	public WorldView()
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
		
		hit = new ParticleEffect();
		hit.load(Gdx.files.internal("data/effects/particle"), Gdx.files.internal("data/effects"));
		hit.start();
		
		for(int i = 0; i < ConfigController.get().getConfig().getNotesCount();i++){
			toneEffect = new ParticleEffect();
			toneEffect.load(Gdx.files.internal("data/effects/tone_" + (i+1) + ".p"), Gdx.files.internal("data/effects"));
			toneEffect.start();
			toneEffects.add(toneEffect);
			
		}
			
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

}
