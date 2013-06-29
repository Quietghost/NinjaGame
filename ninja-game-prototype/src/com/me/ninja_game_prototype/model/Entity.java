package com.me.ninja_game_prototype.model;

import java.util.Observable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends Observable
{
	protected Vector2 position;
	protected Vector2 center;
	protected float width;
	protected float height;
	protected Rectangle bounds;
	protected Texture texture;
	
	public Entity(Vector2 position, float width, float height)
	{
		this.position = position;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(position.x, position.y, width, height);
		this.center = new Vector2(position.x+width/2,position.y+height/2); 
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector2 position)
	{
		this.position = position;
		
		if(bounds!=null)
		{
			this.bounds.x = position.x;
			this.bounds.y = position.y;
		}
	}
	
	public void setPosition(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
		
		if(bounds!=null)
		{
			this.bounds.x = x;
			this.bounds.y = y;
		}
	}
	
	public void addPosition(Vector2 add) {
		setPosition(position.x + add.x,position.y + add.y);
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public void setWidth(float width)
	{
		this.width = width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public void setHeight(float height)
	{
		this.height = height;
	}
	
//	private String getTexturePath()
//	{
//		return ((FileTextureData) texture.getTextureData()).getFileHandle().path();
//	}
	
	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	public Vector2 getCenter()
	{
		this.center.x = this.position.x+width/2;
		this.center.y = this.position.y+height/2; 
		return center;
	}
}
