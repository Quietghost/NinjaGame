package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.controller.InputHandler;
import com.me.ninja_game_prototype.view.Audio;
import com.me.ninja_game_prototype.view.WorldRenderer;

public class World {

	NinjaGamePrototype game;
	Ninja ninja;
	Obstacle obstacle1;
	Obstacle obstacle2;
	Exit exit;
	WorldRenderer worldrenderer;
	boolean gameEnd = false;
	float timeSinceCollision = 0;
	
	public World(NinjaGamePrototype game){
		this.game = game;
		ninja = new Ninja(60, 54, 62, new Vector2(200,400));
		obstacle1 = new Obstacle(new Vector2(320,310), 64, 64);
		obstacle2 = new Obstacle(new Vector2(100,110), 108, 92);
		exit = new Exit(new Vector2(600,110), 54, 100);
		Gdx.input.setInputProcessor(new InputHandler(this));
	}

	/**
	 * @return the n
	 */
	public Ninja getNinja() {
		return ninja;
	}
	
	/**
	 * @return the worldrenderer
	 */
	public WorldRenderer getWorldrenderer() {
		return worldrenderer;
	}

	/**
	 * @param worldrenderer the worldrenderer to set
	 */
	public void setWorldrenderer(WorldRenderer worldrenderer) {
		this.worldrenderer = worldrenderer;
	}

	public void update(){
		
		ninja.update();
		obstacle1.update();
		obstacle2.update();
		
		if (ninja.getBounds().overlaps(obstacle1.getBounds())){
			obstacle1.setRumble(true);
			
			timeSinceCollision += Gdx.graphics.getDeltaTime();
			if(timeSinceCollision > 0.5f) {
				ninja.setPosition(new Vector2(200,400));
				timeSinceCollision = 0;
				game.addATTEMPTS();
			}
			
			if (obstacle1.isRumble() && obstacle1.isAudioFlag()){
				Audio.hitObstacle();
				if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "Hit 1!! ");
				obstacle1.setAudioFlag(false);
			}
			
		}else{
			obstacle1.setRumble(false);
			obstacle1.setAudioFlag(true);
			if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "Else 1!! ");
		}
		
		if (ninja.getBounds().overlaps(obstacle2.getBounds())){
			obstacle2.setRumble(true);

			timeSinceCollision += Gdx.graphics.getDeltaTime();
			if(timeSinceCollision > 0.5f) {
				ninja.setPosition(new Vector2(200,400));
				timeSinceCollision = 0;
				game.addATTEMPTS();
			}
			
			if (obstacle2.isRumble() && obstacle2.isAudioFlag()){
				Audio.hitObstacle();
				if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "Hit 2!! ");
				obstacle2.setAudioFlag(false);
			}
			
		}else{
			obstacle2.setRumble(false);
			obstacle2.setAudioFlag(true);
			if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "Else 2!! ");
		}
		
		if (ninja.getBounds().overlaps(exit.getBounds())){
			if (exit.isAudioFlag()){
				Audio.playExit();
				exit.setAudioFlag(false);
				this.gameEnd = true;
			}
		}
	}
	
	/**
	 * @return the obstacle
	 */
	public Obstacle getObstacle1() {
		return obstacle1;
	}
	
	public Obstacle getObstacle2() {
		return obstacle2;
	}

	/**
	 * @return the exit
	 */
	public Exit getExit() {
		return exit;
	}

	/**
	 * @param exit the exit to set
	 */
	public void setExit(Exit exit) {
		this.exit = exit;
	}

	/**
	 * @return the gameEnd
	 */
	public boolean isGameEnd() {
		return gameEnd;
	}

	/**
	 * @param gameEnd the gameEnd to set
	 */
	public void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
	}

	public void dispose(){
		
	}

}
