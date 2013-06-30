package com.me.ninja_game_prototype.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.me.ninja_game_prototype.helper.SongLoader;
import com.me.ninja_game_prototype.model.SongModel;
import com.me.ninja_game_prototype.model.SongRecorder;

public class SongController {
	/* static */
	
	/* singleton */
	private static SongController instance;

	private SongController() {
		
	}

	public static SongController get() {
		if (SongController.instance == null) {
			SongController.instance = new SongController();
		}
		return SongController.instance;
	}
	
	/* instance */
	private Array<SongModel> songs = new Array<SongModel>();
	private String songPlayed;
	private Array<String> songNotesToProve = new Array<String>();
	
	
	/*Methods*/
	private void loadSongs(){
		SongLoader.get().loadSongs();
	}
	
	public boolean validateSong(Array<String> songNotes){
		
		boolean playedCorrectly = false;
		int correctNotes = 0;
		
		for(int i = 0; i < songs.size; i++){
			
			songNotesToProve = songs.get(i).getSongNotes();
			
			for(int j = 0; j < songNotesToProve.size;j++){
				if(songNotesToProve.get(j).equalsIgnoreCase(SongRecorder.get().getRecordedSong().get(j))){
					correctNotes++;
				}
			}
			
			//TODO Put in config-File
			if (correctNotes == 4){
				playedCorrectly = true;
				SongController.get().setSongPlayed(songs.get(i).getIdentifier());
				i = songs.size;
			}else{
				SongController.get().setSongPlayed("none");
				correctNotes = 0;
			}
		}
		
		SongRecorder.get().getRecordedSong().clear();
		
		return playedCorrectly;
	}
	
	public void addSong(SongModel song){
		this.songs.add(song);
	}

	public String getSongPlayed() {
		return songPlayed;
	}

	public void setSongPlayed(String songPlayed) {
		this.songPlayed = songPlayed;
	}
}
