package com.me.ninja_game_prototype.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.me.ninja_game_prototype.controller.SongController;
import com.me.ninja_game_prototype.model.SongModel;

public class SongLoader {

	/* static */
	
	/* singleton */
	private static SongLoader instance;

	private SongLoader() {}

	public static SongLoader get()
	{
		if (SongLoader.instance == null)
		{
			SongLoader.instance = new SongLoader();
		}
		return SongLoader.instance;
	}
	
	/* instance */
	
	
	public void loadSongs(){
		
		FileHandle file = Gdx.files.internal("data/songs/songs.txt");
		Json  json = new Json();
		
		JsonReader reader = new JsonReader();
		JsonValue value = reader.parse(file);
		
		JsonValue songJson = value.child();
		
		while(songJson != null){
			
			SongModel song = json.readValue(SongModel.class, songJson);
			SongController.get().addSong(song);
			
			songJson = songJson.next();
			
		}
	}
}
