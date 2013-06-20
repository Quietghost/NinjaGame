package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.math.Vector2;

public class ExitModel extends Entity {

	String identifier;
	boolean rumble;
	boolean audioFlag = true;
	
	public ExitModel(Vector2 position, float width, float height) {
		super(position, width, height);
		
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * @return the rumble
	 */
	public boolean isRumble() {
		return rumble;
	}

	/**
	 * @param rumble the rumble to set
	 */
	public void setRumble(boolean rumble) {
		this.rumble = rumble;
	}

	/**
	 * @return the audioFlag
	 */
	public boolean isAudioFlag() {
		return audioFlag;
	}

	/**
	 * @param audioFlag the audioFlag to set
	 */
	public void setAudioFlag(boolean audioFlag) {
		this.audioFlag = audioFlag;
	}
}
