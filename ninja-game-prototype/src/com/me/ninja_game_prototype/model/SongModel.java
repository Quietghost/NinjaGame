package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.utils.Array;

public class SongModel {

	/*instances*/
	String identifier;
	Array<String> songNotes;
	
	public SongModel(){
		
	}
	
	public SongModel(String identifier, Array<String> songNotes){
		this.identifier = identifier;
		this.songNotes = songNotes;
	}

	public Array<String> getSongNotes() {
		return songNotes;
	}

	public void setSongNotes(Array<String> songNotes) {
		this.songNotes = songNotes;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	
}
