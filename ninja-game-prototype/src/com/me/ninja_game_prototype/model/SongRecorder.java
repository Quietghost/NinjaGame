package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.utils.Array;

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
	private Array<String> recordedSong;
	
	public Array<String> getRecordedSong() {
		return recordedSong;
	}


}
