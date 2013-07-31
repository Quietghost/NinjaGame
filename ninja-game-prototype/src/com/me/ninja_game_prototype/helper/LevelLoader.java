package com.me.ninja_game_prototype.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.me.ninja_game_prototype.model.EnemyModel;
import com.me.ninja_game_prototype.model.ExitModel;
import com.me.ninja_game_prototype.model.FloorModel;
import com.me.ninja_game_prototype.model.NinjaModel;
import com.me.ninja_game_prototype.model.ObstacleModel;
import com.me.ninja_game_prototype.model.SpezialFloorModel;
import com.me.ninja_game_prototype.model.WallModel;
import com.me.ninja_game_prototype.model.WorldModel;

public class LevelLoader
{
	/* static */
	
	/* singleton */
	private static LevelLoader instance;

	private LevelLoader() {}

	public static LevelLoader get()
	{
		if (LevelLoader.instance == null)
		{
			LevelLoader.instance = new LevelLoader();
		}
		return LevelLoader.instance;
	}
	
	/* instance */
	public void loadLevel(int level)
	{
		WorldModel.get().clear();
		
		try {
			TmxMapLoader loader = new TmxMapLoader();
	        TiledMap map = loader.load("data/maps/map5.tmx");	        
	        WorldModel.get().setMap(map);
	        
	        @SuppressWarnings("rawtypes")
			Iterator it;
	        
	        WorldModel.get().setExit(new ExitModel((TiledMapTileLayer) map.getLayers().get("L_Exit")));
	        WorldModel.get().setFloor(new FloorModel((TiledMapTileLayer) map.getLayers().get("L_Floor")));
	        WorldModel.get().setWall(new WallModel((TiledMapTileLayer) map.getLayers().get("L_Wall")));
	        
	        // load floors
	        MapLayer spezialfloors = map.getLayers().get("O_Floors");
	        it = spezialfloors.getObjects().iterator();
	        while (it.hasNext())
	        {
	        	PolygonMapObject mo = (PolygonMapObject) it.next();
	        	WorldModel.get().addSpezialFloor(new SpezialFloorModel(mo.getName(), mo.getPolygon()));
			}
	        
	        // load obstacles
	        MapLayer obstacles = map.getLayers().get("O_Obstacles");
	        it = obstacles.getObjects().iterator();
	        while (it.hasNext())
	        {
	        	WorldModel.get().addObstacle(new ObstacleModel((MapObject) it.next()));
			}
	        
	        // load enemies
	        MapLayer pathLayer = map.getLayers().get("O_Enemies_Path");
	        it = pathLayer.getObjects().iterator();
	        Map<String,Polygon> paths = new HashMap<String,Polygon>();
	        while (it.hasNext())
	        {
	        	PolygonMapObject mo = (PolygonMapObject) it.next();
	        	paths.put(mo.getName(), mo.getPolygon());
			}
	        
	        MapLayer enemies = map.getLayers().get("O_Enemies");
	        it = enemies.getObjects().iterator();
	        while (it.hasNext())
	        {
	        	MapObject mo = (MapObject) it.next();
	        	WorldModel.get().addEnemy(new EnemyModel(mo, paths.get(mo.getName())));
			}
	        
	        // load ninja
	        NinjaModel ninja = new NinjaModel(map.getLayers().get("O_Ninja").getObjects().get("day"), map.getLayers().get("O_Ninja").getObjects().get("night"));
	        ninja.setSpeed(60); // TODO load from configuration
	        WorldModel.get().setNinja(ninja);
	        
	        // TODO remove debug
	        // ------------------------------------------
	        // debug
	        /*
	        it = map.getProperties().getKeys();
	        while (it.hasNext())
	        {
	        	String key = (String) it.next();
	        	System.out.println(key+": "+map.getProperties().get(key));
			}	        
	        MapLayers layers = map.getLayers();
	        System.out.println("Layers: "+layers.getCount());
	        
	        // tiled layer
	        TiledMapTileLayer floor = (TiledMapTileLayer) layers.get("L_Floor");
	        it = layers.iterator();
	        while (it.hasNext())
	        {
	        	MapLayer l = (MapLayer) it.next();
	        	System.out.println("Layer: "+l.getName());
			}
	        
	        MapObjects floorO = floor.getObjects();
	        System.out.println("Objects: "+floorO.getCount());
	        MapProperties floorP = floor.getProperties();
	        
	        it = floorO.iterator();
	        while (it.hasNext())
	        {
	        	MapObject o = (MapObject) it.next();
	        	System.out.println("O: "+o.getName());
			}
	        
	        it = floorP.getKeys();
	        while (it.hasNext())
	        {
	        	String key = (String) it.next();
	        	System.out.println("P "+key+": "+floorP.get(key));
			}
	        
	        System.out.println("Layer: "+floor.getHeight()+" "+floor.getWidth()+" - "+floor.getTileHeight()+" "+floor.getTileWidth());
	        Cell c = floor.getCell(1, 1);
	        System.out.println("Cell: "+c.toString());
	        TiledMapTile t = c.getTile();
	        
	        it = t.getProperties().getKeys();
	        while (it.hasNext())
	        {
	        	String key = (String) it.next();
	        	System.out.println("Tile "+key+": "+t.getProperties().get(key));
			}
	        
	        TextureRegion tr = t.getTextureRegion();
	        System.out.println("TextureRegion: "+tr.getRegionHeight()+" "+tr.getRegionWidth()+" - "+tr.getRegionY()+" "+tr.getRegionX());
	        
	        Texture tx = tr.getTexture();
	        System.out.println("Texture: "+tx.toString());
	        
	        FileTextureData td = (FileTextureData) tx.getTextureData();
	        System.out.println("Path: "+td.getFileHandle().path());
	        System.out.println("Name: "+td.getFileHandle().name());
			
	        // object layer
//	        MapLayer enemies = map.getLayers().get("O_Enemies");
	        System.out.println("Enemies: "+enemies.getObjects().getCount());
	        
	        // object layer
//	        MapLayer obstacles = map.getLayers().get("O_Obstacles");
	        System.out.println("Obstacles: "+obstacles.getObjects().getCount());
	        
	        it = obstacles.getObjects().iterator();
	        while (it.hasNext())
	        {
	        	MapObject o = (MapObject) it.next();
	        	System.out.println("O: "+o.getName());
			}
	        
	        it = obstacles.getProperties().getKeys();
	        while (it.hasNext())
	        {
	        	String key = (String) it.next();
	        	System.out.println("P "+key+": "+obstacles.getProperties().get(key));
			}
	        
	        MapObject box = obstacles.getObjects().get("Box_1");
	        
	        // TODO hotfixed in tmx file (gid property added to object)
	        TiledMapTile tile = map.getTileSets().getTile(Integer.valueOf((String)box.getProperties().get("gid")));
	        tr = tile.getTextureRegion();
	        
	        System.out.println("TextureRegion: "+tr.getRegionHeight()+" "+tr.getRegionWidth()+" - "+tr.getRegionY()+" "+tr.getRegionX());
	        
	        tx = tr.getTexture();
	        System.out.println("Texture: "+tx.toString());
	        
	        td = (FileTextureData) tx.getTextureData();
	        System.out.println("Path: "+td.getFileHandle().path());
	        System.out.println("Name: "+td.getFileHandle().name());
	        
	        it = box.getProperties().getKeys();
	        while (it.hasNext())
	        {
	        	String key = (String) it.next();
	        	System.out.println("P "+key+": "+box.getProperties().get(key));
			}
			System.exit(0);
			*/
	        // -- end debug
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
//		WorldModel.get().addObstacles(new ObstacleModel(new Vector2(320,310), 64, 64, ""));
//		WorldModel.get().addObstacles(new ObstacleModel(new Vector2(100,110), 108, 92, ""));
//		WorldModel.get().setNinja(new NinjaModel(60, 54, 62, new Vector2(200,400), "", ""));
//		WorldModel.get().setExit(new ExitModel(new Vector2(600,110), 54, 100, ""));
	}
}
