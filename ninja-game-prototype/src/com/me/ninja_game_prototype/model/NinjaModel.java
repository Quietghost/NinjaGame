package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.controller.NinjaController;
import com.me.ninja_game_prototype.helper.MapObjectDateGatherer;

public class NinjaModel extends MovableEntity
{
	private static final float RUNNING_FRAME_DURATION = 0.2f;
	
	private Vector2 goal = new Vector2();
	private Vector2 startPosition = new Vector2();
	private int flagInput;
	private Texture nightTexture;

	/** Textures **/
	private TextureRegion idlePipePlaying;
	
	/** Animations **/
	private float stateTime = 0;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	private Animation playingPipeAnimation;
	private Animation idleLightAnimation;
	private Animation idleNightAnimation;
	
	public NinjaModel(MapObject day, MapObject night)
	{
		super(0, new Vector2(0,0), 0, 0);
		
		MapObjectDateGatherer.gather(night, this);
		this.nightTexture = getTexture();
		
		float w = getWidth(); float h = getHeight();
		MapObjectDateGatherer.gather(day, this);
		
		// Do ninja textures day and night have different dimensions?
		if (getWidth()!=w || getHeight()!=h) System.exit(0);
		
		this.startPosition = new Vector2(getPosition().x, getPosition().y);
		
		// annimated sprite
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/animations/ninja.pack"));
		
		idlePipePlaying = atlas.findRegion("Ninja_JinLi_flute");
		
		TextureRegion[] idleLight = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			idleLight[i] = atlas.findRegion("Ninja_JinLi_idle", i + 1);
		}
		idleLightAnimation = new Animation(RUNNING_FRAME_DURATION, idleLight);
		
		TextureRegion[] idleNight = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			idleNight[i] = atlas.findRegion("Ninja_JinLi_dark", i + 1);
		}
		idleNightAnimation = new Animation(RUNNING_FRAME_DURATION, idleNight);
		
		TextureRegion[] walkLeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkLeftFrames[i] = atlas.findRegion("Ninja_JinLi_dark", i + 1);
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[3];

		for (int i = 0; i < 3; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		
		TextureRegion[] playingPipeFrames = new TextureRegion[3];

		for (int i = 0; i < 3; i++) {
			playingPipeFrames[i] = atlas.findRegion("Ninja_JinLi_flute", i + 1);
		}
		playingPipeAnimation = new Animation(RUNNING_FRAME_DURATION, playingPipeFrames);
		
	}

	public void update()
	{
		stateTime += Gdx.graphics.getDeltaTime();
		
		float preX = getPosition().x;
		float preY = getPosition().y;
		
		switch (flagInput)
		{
			case 1:
				if (!getBounds().contains(getGoal()) && getPosition().dst(getGoal()) > 5)
				{		
					// TODO try this: position.add(velocity.tmp().mul(delta)); 
					addPosition(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
				}else{
					setPosition(new Vector2(getGoal().x - getBounds().width/2, goal.y - getBounds().height/2));
					setVelocity(new Vector2(0, 0));
					NinjaController.stopWalk();
				}
				break;
			case 2:
				addPosition(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
				break;
		}
		
		// no ghost
		if (WorldModel.get().getWall().overlaps(this))
		{
			setPosition(preX, preY);
		}

		// Exit?
		if (WorldModel.get().getExit().overlaps(this))
		{
			// TODO audio controller
//			if (exit.isAudioFlag()){
//				GameAudio.playExit();
//				exit.setAudioFlag(false);
//			}
			GameModel.get().setGameEnd(true);
		}

		setChanged();
		notifyObservers(ObserverMessage.NINJA);
	}

	public Vector2 getGoal()
	{
		return goal;
	}

	public void setGoal(Vector2 goal)
	{
		
		this.goal = goal;
	}

	public int getFlagInput()
	{
		return flagInput;
	}

	public void setFlagInput(int flagInput)
	{
		this.flagInput = flagInput;
	}

	public Texture getNightTexture()
	{
		return nightTexture;
	}

	public void setNightTexture(Texture nightTexture)
	{
		this.nightTexture = nightTexture;
	}

	public void resetStartPosition() {
		setPosition(startPosition.x, startPosition.y);
	}
	
	public boolean facingLeft()
	{
		return getVelocity().x<0;
	}

	public TextureRegion getIdleLight() {
		return idleLightAnimation.getKeyFrame(stateTime/3, true);
	}
	
	public TextureRegion getIdleNight() {
		return idleNightAnimation.getKeyFrame(stateTime/3, true);
	}

	public TextureRegion getWalk() {
		
		return facingLeft() ? walkLeftAnimation.getKeyFrame(stateTime, true) : walkRightAnimation.getKeyFrame(stateTime, true);
	}
	
	public TextureRegion getPipePlaying() {
		
		return playingPipeAnimation.getKeyFrame(stateTime/3, true);
	}
	
	public TextureRegion getIdlePipePlaying() {
		
		return idlePipePlaying;
	}
}
