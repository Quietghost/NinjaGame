package com.me.ninja_game_prototype.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.me.ninja_game_prototype.NinjaGamePrototype;

public class WorldModel extends Observable
{
	/* static */
	
	/* singleton */
	private static WorldModel instance;

	private WorldModel()
	{
	}

	public static WorldModel get()
	{
		if (WorldModel.instance == null)
		{
			WorldModel.instance = new WorldModel();
		}
		return WorldModel.instance;
	}
	
	/* instance */
	private TiledMap map;
	private List<SpezialFloorModel> spezialfloors = new ArrayList<SpezialFloorModel>();
	private List<ObstacleModel> obstacles = new ArrayList<ObstacleModel>();
	private List<EnemyModel> enemies = new ArrayList<EnemyModel>();
	private ExitModel exit;
	private NinjaModel ninja;
	private FloorModel floor;
	private WallModel wall;
	private boolean night = false;
	private boolean menuShown = false;

	public void clear()
	{
		map = null;
		obstacles = new ArrayList<ObstacleModel>();
		exit = null;
		floor = null;
		wall = null;
		ninja = null;
	}

	public List<ObstacleModel> getObstacles()
	{
		return obstacles;
	}

	public void addObstacle(ObstacleModel obstacle)
	{
		this.obstacles.add(obstacle);
	}
	
	public void removeObstacle(ObstacleModel obstacle)
	{
		this.obstacles.remove(obstacle);
	}

	public ExitModel getExit()
	{
		return exit;
	}

	public void setExit(ExitModel exit)
	{
		this.exit = exit;
	}

	public NinjaModel getNinja()
	{
		return ninja;
	}

	public void setNinja(NinjaModel ninja)
	{
		this.ninja = ninja;
	}
	
	public FloorModel getFloor()
	{
		return floor;
	}

	public void setFloor(FloorModel floor)
	{
		this.floor = floor;
	}

	public WallModel getWall()
	{
		return wall;
	}

	public void setWall(WallModel wall)
	{
		this.wall = wall;
	}
	
	public boolean isMenuShown() {
		return menuShown;
	}

	public void setMenuShown(boolean menuShown) {
		this.menuShown = menuShown;
	}

	public void update()
	{
		ninja.update();
		for (ObstacleModel obstacle : obstacles)
		{
			obstacle.update();
		
			if (ninja.getBounds().overlaps(obstacle.getBounds()))
			{
				obstacle.setRumble(true);
				
				GameModel.get().addTimeSinceCollision(Gdx.graphics.getDeltaTime());
				if(GameModel.get().getTimeSinceCollision() > 0.5f)
				{
					ninja.resetStartPosition();
					GameModel.get().setTimeSinceCollision(0);
					GameModel.get().addAttempt();
				}
				
				if (obstacle.isRumble() && obstacle.isAudioFlag())
				{
					//NinjaController.hitObstacle();
					setChanged();
					notifyObservers(ObserverMessage.Collision_Obstacle);
					
					if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "Hit 1!! ");
					obstacle.setAudioFlag(false);
				}
				
			}
			else
			{
				
				obstacle.setRumble(false);
				obstacle.setAudioFlag(true);
			}
		}
		
		for (EnemyModel enemy : enemies)
		{
			enemy.update();
		
			if (ninja.getBounds().overlaps(enemy.getBounds()))
			{
				
				enemy.setRumble(true);
				
				GameModel.get().addTimeSinceCollision(Gdx.graphics.getDeltaTime());
				if(GameModel.get().getTimeSinceCollision() > 0.5f)
				{
					ninja.resetStartPosition();
					GameModel.get().setTimeSinceCollision(0);
					GameModel.get().addAttempt();
				}
				
				if (enemy.isRumble() && enemy.isAudioFlag())
				{
					//NinjaController.hitObstacle();
					setChanged();
					notifyObservers(ObserverMessage.Collision_Enemy);
					
					if (NinjaGamePrototype.DEBUG) Gdx.app.log(NinjaGamePrototype.LOG, "Hit 2!! ");
					enemy.setAudioFlag(false);
				}
				
			}
			else
			{
				enemy.setRumble(false);
				enemy.setAudioFlag(true);
			}
		}
	}
	
	public void dispose()
	{
		
	}

	public TiledMap getMap()
	{
		return map;
	}

	public void setMap(TiledMap map)
	{
		this.map = map;
	}

	public boolean isNight()
	{
		return night;
	}

	public void setNight(boolean night)
	{
		this.night = night;
	}

	public List<EnemyModel> getEnemies()
	{
		return enemies;
	}

	public void addEnemy(EnemyModel enemy)
	{
		this.enemies.add(enemy);
	}
	
	public void removeEnemy(EnemyModel enemy)
	{
		this.enemies.remove(enemy);
	}

	public List<SpezialFloorModel> getSpezialFloors()
	{
		return spezialfloors;
	}

	public void addSpezialFloor(SpezialFloorModel spezialfloor)
	{
		this.spezialfloors.add(spezialfloor);
	}
	
	public void removeSpezialFloor(SpezialFloorModel spezialfloor)
	{
		this.spezialfloors.remove(spezialfloor);
	}
}
