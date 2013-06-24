package com.me.ninja_game_prototype.model;

import java.util.List;

import com.badlogic.gdx.utils.Array;

public class SongModel {
	/* static */
	
	/* singleton */
	private static SongModel instance;

	private SongModel() {
		loadSongs();
	}

	public static SongModel get() {
		if (SongModel.instance == null) {
			SongModel.instance = new SongModel();
		}
		return SongModel.instance;
	}
	
	/* instance */
	List<Song> songs;
	
	
	/*Methods*/
	private void loadSongs(){
		
	}
	
	public void validateSong(Array<String> songNotes){
		
	}
}
