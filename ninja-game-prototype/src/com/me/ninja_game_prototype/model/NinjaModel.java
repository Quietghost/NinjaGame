package com.me.ninja_game_prototype.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.me.ninja_game_prototype.helper.MapObjectDataGatherer;

public class NinjaModel extends MovableEntity
{
	private static final float RUNNING_FRAME_DURATION = 0.2f;
	static final int keyboard = 2;
	static final int touch_mouse = 1;
	
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
		
		MapObjectDataGatherer.gather(night, this);
		this.nightTexture = getTexture();
		
		float w = getWidth(); float h = getHeight();
		MapObjectDataGatherer.gather(day, this);
		
		// Do ninja textures day and night have different dimensions?
		if (getWidth()!=w || getHeight()!=h) System.exit(0);
		
		this.startPosition = new Vector2(getPosition().x, getPosition().y);
		
		// annimated sprite
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/animations/ninja_pixel.pack"));
		
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
			case touch_mouse:
				if (!getBounds().contains(getGoal()) && getPosition().dst(getGoal()) > 5)
				{		
					// TODO try this: position.add(velocity.tmp().mul(delta)); 
					addPosition(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
					
					setChanged();
					notifyObservers(ObserverMessage.Ninja_Walk);
					
				}else{
					setPosition(new Vector2(getGoal().x - getBounds().width/2, goal.y - getBounds().height/2));
					setVelocity(new Vector2(0, 0));
					
					setChanged();
					notifyObservers(ObserverMessage.Ninja_Walk_Stop);
				}
				break;
			case keyboard:
				setChanged();
				notifyObservers(ObserverMessage.Ninja_Walk);
				
				addPosition(getVelocity().cpy().scl(Gdx.graphics.getDeltaTime() * getSpeed()));
				break;
			default:
				WorldModel.get().getNinja().setFlagInput(0);
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
