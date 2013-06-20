package com.me.ninja_game_prototype.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.audio.GameAudio;
import com.me.ninja_game_prototype.view.WorldView;

public class WorldModel extends Observable
{
	/* static */
	
	/* singleton */
	private static WorldModel instance;

	private WorldModel() {
	}

	public static WorldModel get() {
		if (WorldModel.instance == null) {
			WorldModel.instance = new WorldModel();
		}
		return WorldModel.instance;
	}
	
	/* instance */
	NinjaGamePrototype game; // TODO refactore 
	WorldView worldrenderer; // TODO refactore 
	float timeSinceCollision = 0; // TODO refactore 
	
	// refactored
	private List<ObstacleModel> obstacles = new ArrayList<ObstacleModel>();
	private ExitModel exit;
	private NinjaModel ninja;

	public void clear() {
		obstacles = new ArrayList<ObstacleModel>();
		exit = null;
		ninja = null;
	}

	public List<ObstacleModel> getObstacles() {
		return obstacles;
	}

	public void addObstacles(ObstacleModel obstacle) {
		this.obstacles.add(obstacle);
	}
	
	public void removeObstacles(ObstacleModel obstacle) {
		this.obstacles.remove(obstacle);
	}

	public ExitModel getExit() {
		return exit;
	}

	public void setExit(ExitModel exit) {
		this.exit = exit;
	}

	public NinjaModel getNinja() {
		return ninja;
	}

	public void setNinja(NinjaModel ninja) {
		this.ninja = ninja;
	}
	
	public void update(){
		
		ninja.update();
		for (ObstacleModel obstacle : obstacles) {
			obstacle.update();
		
			if (ninja.entity.getBounds().overlaps(obstacle.getBounds())){
				obstacle.setRumble(true);
				
				timeSinceCollision += Gdx.graphics.getDeltaTime();
				if(timeSinceCollision > 0.5f) {
					ninja.entity.setPosition(new Vector2(200,400));
					timeSinceCollision = 0;
					game.addATTEMPTS();
				}
				
				if (obstacle.isRumble() && obstacle.isAudioFlag()){
					GameAudio.hitObstacle();
					if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "Hit 1!! ");
					obstacle.setAudioFlag(false);
				}
				
			}else{
				obstacle.setRumble(false);
				obstacle.setAudioFlag(true);
				if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "Else 1!! ");
			}
		}
		
		if (ninja.entity.getBounds().overlaps(exit.getBounds())){
			if (exit.isAudioFlag()){
				GameAudio.playExit();
				exit.setAudioFlag(false);
				GameModel.get().setGameEnd(true);
			}
		}
	}
	
	public void dispose(){
		
	}

}
