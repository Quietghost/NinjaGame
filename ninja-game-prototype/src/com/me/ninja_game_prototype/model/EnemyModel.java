package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.helper.MapObjectDateGatherer;

public class EnemyModel extends MovableEntity
{
	private Vector2 goal;
	private Polygon path;
	private int pathIndex = 0;
	
	public EnemyModel(MapObject o, Polygon path)
	{
		super(0, new Vector2(0,0), 0, 0);
		MapObjectDateGatherer.gather(o, this);
		setSpeed(30);
		
		this.path = path; 
	}

	public void update()
	{
		if (path==null) return;
		if (goal==null) goal = new Vector2(getTransformedVertices()[pathIndex],getTransformedVertices()[pathIndex+1]);
		if (goal.dst(getCenter())<5)
		{
			pathIndex+=2;
			if (pathIndex>=getTransformedVertices().length) pathIndex=0;
			
			goal.x = getTransformedVertices()[pathIndex];
			goal.y = getTransformedVertices()[pathIndex+1];
		}
		
		float xDiff = goal.x-getCenter().x;
		float yDiff = goal.y-getCenter().y;
		getVelocity().x = xDiff / (Math.abs(xDiff)+Math.abs(yDiff));
		getVelocity().y = yDiff / (Math.abs(xDiff)+Math.abs(yDiff));
		
		addPosition(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
	}

	public float[] getTransformedVertices() {
		if (path==null) return new float[0];
		return path.getTransformedVertices();
	}
}