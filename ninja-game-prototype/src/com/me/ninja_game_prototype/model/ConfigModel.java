package com.me.ninja_game_prototype.model;

public class ConfigModel {
	/*instances*/
	int notesCount;
	
	public ConfigModel(){
		
	}
	
	public ConfigModel(int notesCount){
		this.notesCount = notesCount;
	
	}

	public int getNotesCount() {
		return notesCount;
	}

	public void setNotesCount(int notesCount) {
		this.notesCount = notesCount;
	}
}
