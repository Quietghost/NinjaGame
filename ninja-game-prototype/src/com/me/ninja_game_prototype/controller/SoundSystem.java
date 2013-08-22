package com.me.ninja_game_prototype.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.me.ninja_game_prototype.NinjaGamePrototype;
import com.me.ninja_game_prototype.model.EnemyModel;
import com.me.ninja_game_prototype.model.ObserverMessage;
import com.me.ninja_game_prototype.model.WorldModel;

public class SoundSystem implements Observer{

	static Sound floor_wood = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_wood.ogg"));
	static Sound floor_glass = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_glass.ogg"));
	static Sound floor_grass = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_grass.ogg"));
	static Sound floor_leaves = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_leaves.ogg"));
	static Sound floor_leaves_1 = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_leaves_1.ogg"));
	static Sound floor_snow = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_snow.ogg"));
	static Sound floor_sand = Gdx.audio.newSound(Gdx.files.internal("data/sounds/floor_sand.ogg"));
	static Sound hit_obstacle = Gdx.audio.newSound(Gdx.files.internal("data/sounds/hit_obstacle.ogg"));
	static Sound exit = Gdx.audio.newSound(Gdx.files.internal("data/sounds/celebration.mp3"));
	
	/* static */
	
	private Sound floor_sound;
	private Sound enemy_sound;
	private Map<EnemyModel, Float> enemy_distances = new HashMap<EnemyModel, Float>();
	private long soundid;
		
	/* singleton */
	private static SoundSystem instance;

	private SoundSystem()
	{
	}

	public static SoundSystem get()
	{
		if (SoundSystem.instance == null)
		{
			SoundSystem.instance = new SoundSystem();
		}
		return SoundSystem.instance;
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof EnemyModel){
			//Gdx.app.log(NinjaGamePrototype.LOG, "Enemy " + arg.toString());
			if (!enemy_distances.containsKey(o)){
				enemy_distances.put((EnemyModel) o, 0f);
			}
			
			for(EnemyModel e : enemy_distances.keySet()){
				float dist = e.getPosition().dst(WorldModel.get().getNinja().getPosition());
				//if (dist < enemy_distances.get(e) + 5){
					enemy_distances.put(e, dist);
				//}
				
				if (enemy_distances.get(e) < 100){
					
					if (!e.isWalkingSound()){
						e.setWalkingSound(true);
						soundid = floor_sand.loop();
					}
					
					if (e.isWalkingSound()){
						enemy_sound = floor_sand;
						enemy_sound.setVolume(soundid, Math.abs((enemy_distances.get(e)/100) - 1));
					}
					
				}else{
					if (e.isWalkingSound()){
						floor_sand.stop();
						e.setWalkingSound(false);
					}
				}
			}
			
			
		}else{
			
			switch ((ObserverMessage)arg){
			
			case Ninja_Walk:
				//Gdx.app.log(NinjaGamePrototype.LOG, "Ninja_Walk " + arg.toString());
				if(WorldModel.get().getNinja().getWalking() == 0){
					floor_sound = floor_wood;
					floor_sound.loop();
					WorldModel.get().getNinja().setWalking(1);
				}
				
				break;
				
			case Ninja_Walk_Stop:	
				floor_sound.stop();
				WorldModel.get().getNinja().setWalking(0);
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
	
	public void playExit(){
		exit.play();
	}
	
	public void stopExit(){
		exit.stop();
	}

}
