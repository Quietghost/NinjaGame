package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.utils.Array;
import com.me.ninja_game_prototype.controller.ConfigController;

public class SongModel {

	/* singleton */
	private static SongModel instance;

	public static SongModel get()
	{
		if (SongModel.instance == null)
		{
			SongModel.instance = new SongModel();
		}
		return SongModel.instance;
	}
	
	
	/*instances*/
	String identifier;
	Array<String> songNotes;
	private String tonePlayed = "";
	
	public void init(String identifier, Array<String> songNotes){
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
	
	public String getTonePlayed() {
		return tonePlayed;
	}
	
	public void setTonePlayed(String tonePlayed) {
		this.tonePlayed = tonePlayed;
	}
	
}
