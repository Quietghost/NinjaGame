package com.me.ninja_game_prototype.model;

public enum ObserverMessage {
	
	Ninja_Walk(1), Ninja_Walk_Stop(2), Collision_Obstacle(3), Collision_Obstacle_Stop(4), Collision_Enemy(5), Collision_Enemy_Stop(6), Enemy_Walk(7);

    private int code;

    ObserverMessage(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
}
