package com.me.ninja_game_prototype.controller;

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
	private String songToRecord;
	private Array<String> songNotesToProve = new Array<String>();
	
	
	/*Methods*/
	private void loadSongs(){
		SongLoader.get().loadSongs();
	}
	
	public boolean validateSong(Array<String> songNotes){
		
		boolean playedCorrectly = true;
		
		for(int i = 0; i < songs.size; i++){
			if(songToRecord.equalsIgnoreCase(songs.get(i).getIdentifier())){
				songNotesToProve = songs.get(i).getSongNotes();
			}
		}
		
		for(int i = 0; i < songNotesToProve.size;i++){
			if(songNotesToProve.get(i).equalsIgnoreCase(SongRecorder.get().getRecordedSong().get(i))){
			}else{
				playedCorrectly = false;
			}
		}
		
		SongRecorder.get().getRecordedSong().clear();
		
		return playedCorrectly;
	}
	
	public void addSong(SongModel song){
		this.songs.add(song);
	}

	public String getSongToRecord() {
		return songToRecord;
	}

	public void setSongToRecord(String songToRecord) {
		this.songToRecord = songToRecord;
	}
}
