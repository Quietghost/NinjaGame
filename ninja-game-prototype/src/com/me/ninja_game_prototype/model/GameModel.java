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

	public boolean isGameEnd() {
		return gameEnd;
	}

	public void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
	}
}
