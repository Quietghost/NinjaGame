package com.me.ninja_game_prototype.helper;

import com.badlogic.gdx.utils.Array;
import com.me.ninja_game_prototype.controller.ConfigController;

public class SongRecorder {
	/* static */
	
	/* singleton */
	private static SongRecorder instance;

	private SongRecorder() {
	}

	public static SongRecorder get() {
		if (SongRecorder.instance == null) {
			SongRecorder.instance = new SongRecorder();
		}
		return SongRecorder.instance;
	}
	
	/* instance */
	private Array<String> recordedSong = new Array<String>();
	
	public Array<String> getRecordedSong() {
		return recordedSong;
	}
	
	public boolean isRecorded(){
		
		if(this.recordedSong.size == ConfigController.get().getConfig().getNotesCount()){
			return true;
		}else{
			return false;
		}
	}

}
