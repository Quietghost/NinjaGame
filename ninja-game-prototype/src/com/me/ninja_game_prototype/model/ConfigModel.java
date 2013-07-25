package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.utils.Array;

public class ConfigModel {
	/*instances*/
	int notesCount;
	Array<String> toneKeys;
	
	public ConfigModel(){
		
	}
	
	public ConfigModel(int notesCount, Array<String> toneKeys){
		this.notesCount = notesCount;
		this.toneKeys = toneKeys;
	
	}

	public int getNotesCount() {
		return notesCount;
	}

	public void setNotesCount(int notesCount) {
		this.notesCount = notesCount;
	}

	public Array<String> getToneKeys() {
		return toneKeys;
	}

	public String getToneKey(int index) {
		return toneKeys.get(index);
	}
	
	public int getToneKeyIndex(String toneKey) {
		return toneKeys.indexOf(toneKey, false);
	}
}
