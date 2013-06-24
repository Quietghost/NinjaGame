package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.utils.Array;

public class Song {

	public Song(String identifier){
		this.identifier = identifier;
	}
	
	/*instances*/
	String identifier;
	Array<String> songNotes;
	
	

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
