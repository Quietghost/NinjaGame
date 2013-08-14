package com.me.ninja_game_prototype.controller;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.model.EnemyModel;
import com.me.ninja_game_prototype.model.ObserverMessage;

public class SoundSystem implements Observer{

	static Sound floor_wood = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_wood.ogg"));
	static Sound floor_glass = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_glass.ogg"));
	static Sound floor_grass = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_grass.ogg"));
	static Sound floor_leaves = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_leaves.ogg"));
	static Sound floor_leaves_1 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_leaves_1.ogg"));
	static Sound floor_snow = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_snow.ogg"));
	static Sound floor_sand = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_sand.ogg"));
	
	static Sound hit_obstacle = Gdx.audio.newSound(Gdx.files.internal("data/sounds/hit_obstacle.ogg"));
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof EnemyModel){
			Gdx.app.log(NinjaGamePrototype.LOG, "Enemy " + arg.toString());
		}else{
			
			switch ((ObserverMessage)arg){
			
			case Ninja_Walk:
				Gdx.app.log(NinjaGamePrototype.LOG, "Ninja_Walk " + arg.toString());
				break;
				
			case Ninja_Walk_Stop:	
				Gdx.app.log(NinjaGamePrototype.LOG, "Ninja_Walk_Stop " + arg.toString());
				break;
				
			case Collision_Obstacle:
				Gdx.app.log(NinjaGamePrototype.LOG, "Collision_Obstacle " + arg.toString());
				break;
				
			case Collision_Obstacle_Stop:
				Gdx.app.log(NinjaGamePrototype.LOG, "Collision_Obstacle_Stop " + arg.toString());
				break;
				
			case Collision_Enemy:
				Gdx.app.log(NinjaGamePrototype.LOG, "Collision_Enemy " + arg.toString());
				break;
				
			case Collision_Enemy_Stop:
				Gdx.app.log(NinjaGamePrototype.LOG, "Collision_Enemy_Stop " + arg.toString());
				break;
				
				
			default:	
				break;
			
			
			}
			
			
			
		}
	}

}
