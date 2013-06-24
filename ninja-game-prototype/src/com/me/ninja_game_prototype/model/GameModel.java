package com.me.ninja_game_prototype.model;

public class GameModel {
	/* static */
	
	/* singleton */
	private static GameModel instance;

	private GameModel() {
	}

	public static GameModel get() {
		if (GameModel.instance == null) {
			GameModel.instance = new GameModel();
		}
		return GameModel.instance;
	}
	
	/* instance */
	private boolean gameEnd = false;
	private int attempts = 0;
	private float timeSinceCollision = 0; 
	private boolean songModeShow = false;
	private boolean songModeHide = false;


	public boolean isGameEnd() {
		return gameEnd;
	}

	public void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
	}
	
	public int getAttempts() {
		return attempts;
	}

	public void addAttempt() {
		attempts += 1;
	}

	public float getTimeSinceCollision() {
		return timeSinceCollision;
	}

	public void setTimeSinceCollision(float timeSinceCollision) {
		this.timeSinceCollision = timeSinceCollision;
	}

	public void addTimeSinceCollision(float deltaTime) {
		this.timeSinceCollision += deltaTime;
	}

	public boolean isSongModeShow() {
		return songModeShow;
	}

	public void setSongModeShow(boolean songModeShow) {
		this.songModeShow = songModeShow;
	}
	
	public boolean isSongModeHide() {
		return songModeHide;
	}

	public void setSongModeHide(boolean songModeHide) {
		this.songModeHide = songModeHide;
	}
}
