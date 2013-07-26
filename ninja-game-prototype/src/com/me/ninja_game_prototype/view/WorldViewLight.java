package com.me.ninja_game_prototype.view;

import java.util.List;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.model.EnemyModel;
import com.me.ninja_game_prototype.model.ExitModel;
import com.me.ninja_game_prototype.model.FloorModel;
import com.me.ninja_game_prototype.model.NinjaModel;
import com.me.ninja_game_prototype.model.ObstacleModel;
import com.me.ninja_game_prototype.model.WallModel;
import com.me.ninja_game_prototype.model.WorldModel;

public class WorldViewLight extends WorldView
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
	
	public void init()
	{
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
