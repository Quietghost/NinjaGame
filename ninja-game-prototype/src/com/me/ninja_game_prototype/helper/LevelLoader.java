package com.me.ninja_game_prototype.helper;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.me.ninja_game_prototype.model.WorldModel;

public class LevelLoader {
	/* static */
	
	/* singleton */
	private static LevelLoader instance;

	private LevelLoader() {
	}

	public static LevelLoader get() {
		if (LevelLoader.instance == null) {
			LevelLoader.instance = new LevelLoader();
		}
		return LevelLoader.instance;
	}
	
	/* instance */
	public void loadLevel(int level)
	{
		WorldModel.get().clear();
		
		try {
	        TiledMap map = new TmxMapLoader().load("map3.tmx");
	        Iterator it;
	        
	        it = map.getProperties().getKeys();
	        while (it.hasNext()) {
	        	String key = (String) it.next();
	        	System.out.println(key+": "+map.getProperties().get(key));
			}	        
	        
	        MapLayers layers = map.getLayers();
	        System.out.println("Layers: "+layers.getCount());
	        TiledMapTileLayer floor = (TiledMapTileLayer) layers.get("L_Floor");
	        TiledMapTileLayer wall = (TiledMapTileLayer) layers.get("L_Wall");
	        TiledMapTileLayer exit = (TiledMapTileLayer) layers.get("L_Exit");
	        
	        it = layers.iterator();
	        while (it.hasNext()) {
	        	MapLayer l = (MapLayer) it.next();
	        	System.out.println("Layer: "+l.getName());
			}
	        
	        MapObjects floorO = floor.getObjects();
	        System.out.println("Objects: "+floorO.getCount());
	        MapProperties floorP = floor.getProperties();
	        
	        it = floorO.iterator();
	        while (it.hasNext()) {
	        	MapObject o = (MapObject) it.next();
	        	System.out.println("O: "+o.getName());
			}
	        
	        it = floorP.getKeys();
	        while (it.hasNext()) {
	        	String key = (String) it.next();
	        	System.out.println("P "+key+": "+floorP.get(key));
			}
	        it = floorP.getValues();
	        while (it.hasNext()) {
	        	String value = (String) it.next();
	        	System.out.println("P "+value);
			}
	        
	        System.out.println("Layer: "+floor.getHeight()+" "+floor.getWidth()+" - "+floor.getTileHeight()+" "+floor.getTileWidth());
	        Cell c = floor.getCell(1, 1);
	        System.out.println("Cell: "+c.toString());
	        TiledMapTile t = c.getTile();
	        
	        it = t.getProperties().getKeys();
	        while (it.hasNext()) {
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
			
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.exit(0);
		
//		WorldModel.get().addObstacles(new ObstacleModel(new Vector2(320,310), 64, 64), "");
//		WorldModel.get().addObstacles(new ObstacleModel(new Vector2(100,110), 108, 92), "");
//		
//		WorldModel.get().setNinja(new NinjaModel(60, 54, 62, new Vector2(200,400)), "", "");
//		
//		WorldModel.get().setExit(new ExitModel(new Vector2(600,110), 54, 100), "");
	}
}
