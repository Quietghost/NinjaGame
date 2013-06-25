package com.me.ninja_game_prototype.model;

import java.util.Observable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends Observable
{
	protected Vector2 position;
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
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector2 position)
	{
		this.position = position;
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
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}
	
	private String getTexturePath()
	{
		return ((FileTextureData) texture.getTextureData()).getFileHandle().path();
	}
	
	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
}
