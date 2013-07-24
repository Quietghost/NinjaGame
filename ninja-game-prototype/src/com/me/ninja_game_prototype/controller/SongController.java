package com.me.ninja_game_prototype.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.me.ninja_game_prototype.audio.GameAudio;
import com.me.ninja_game_prototype.helper.SongRecorder;
import com.me.ninja_game_prototype.model.GameModel;
import com.me.ninja_game_prototype.model.SongModel;

public class SongController implements InputProcessor {
	/* static */
	
	/* singleton */
	private static SongController instance;

	private SongController()
	{
		Gdx.input.setInputProcessor(this);
	}

	public static SongController get() {
		if (SongController.instance == null) {
			SongController.instance = new SongController();
		}
		return SongController.instance;
	}
	
	/* instance */
	private Array<SongModel> songs = new Array<SongModel>();
	private String songPlayed;
	private Array<String> songNotesToProve = new Array<String>();
	
	
	/* methods */
	
	// no warnings please
	
//	private void loadSongs(){
//		SongLoader.get().loadSongs();
//	}
	
	public boolean validateSong(Array<String> songNotes){
		
		boolean playedCorrectly = false;
		int correctNotes = 0;
		
		for(int i = 0; i < songs.size; i++){
			
			songNotesToProve = songs.get(i).getSongNotes();
			
			for(int j = 0; j < songNotesToProve.size;j++){
				if(songNotesToProve.get(j).equalsIgnoreCase(SongRecorder.get().getRecordedSong().get(j))){
					correctNotes++;
				}
			}
			
			if (correctNotes == ConfigController.get().getConfig().getNotesCount()){
				playedCorrectly = true;
				SongController.get().setSongPlayed(songs.get(i).getIdentifier());
				i = songs.size;
			}else{
				SongController.get().setSongPlayed("none");
				correctNotes = 0;
			}
		}
		
		SongRecorder.get().getRecordedSong().clear();
		
		return playedCorrectly;
	}
	
	public void replaySong(String identifier){
		
		switch(identifier){
			case "song_blind_guards":
				GameAudio.playPipeSong1();
				break;
			case "song_eleminate_obstacle":
				
				break;
			case "song_irritate_guards":
				
				break;
			case "song_make_light":
				
				break;
			case "song_teleport_me":
				
				break;
		}
		
	}
	
	public void addSong(SongModel song){
		this.songs.add(song);
	}

	public String getSongPlayed() {
		return songPlayed;
	}

	public void setSongPlayed(String songPlayed) {
		this.songPlayed = songPlayed;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		if (GameModel.get().isSongModeShow()){
			switch(keycode){
				case Keys.NUM_1:
					if(!SongRecorder.get().isRecorded())
						GameAudio.playPipeTune1();
						SongRecorder.get().getRecordedSong().add("1");
					break;
				case Keys.NUM_2:
					if(!SongRecorder.get().isRecorded())
						GameAudio.playPipeTune2();
						SongRecorder.get().getRecordedSong().add("2");
					break;
				case Keys.NUM_3:
					if(!SongRecorder.get().isRecorded())
						GameAudio.playPipeTune3();
						SongRecorder.get().getRecordedSong().add("3");
					break;
				case Keys.NUM_4:
					if(!SongRecorder.get().isRecorded())
						GameAudio.playPipeTune4();
						SongRecorder.get().getRecordedSong().add("4");
					break;
				case Keys.O:
					if (GameModel.get().isSongModeShow()){
						GameModel.get().setSongModeHide(true);
						GameModel.get().setSongModeShow(false);
					}else{
						GameModel.get().setSongModeHide(false);
						GameModel.get().setSongModeShow(true);
					}
					break;
			}
			
			if(SongRecorder.get().isRecorded()){
				if(SongController.get().validateSong(SongRecorder.get().getRecordedSong())){
					Gdx.app.log("SongController", SongController.get().getSongPlayed());
					Gdx.app.log("SongController", "success");
					
					final Timer timer = new Timer();  
			        timer.scheduleTask(new Timer.Task() {
						@Override
						public void run() {
							SongController.get().replaySong(SongController.get().getSongPlayed());  
						}
			        }, 1f); 
			        	
					GameModel.get().setSongModeHide(true);
					GameModel.get().setSongModeShow(false);
				}else{
					Gdx.app.log("SongController", SongController.get().getSongPlayed());
					Gdx.app.log("SongController", "failed");
					GameModel.get().setSongModeHide(true);
					GameModel.get().setSongModeShow(false);
				}
			}
		}
		else
		{
			switch(keycode){
			case Keys.O:
				if (GameModel.get().isSongModeShow()){
					GameModel.get().setSongModeHide(true);
					GameModel.get().setSongModeShow(false);
				}else{
					GameModel.get().setSongModeHide(false);
					GameModel.get().setSongModeShow(true);
				}
				break;
		}
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
