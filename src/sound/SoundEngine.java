package sound;


import java.util.HashMap;
import java.util.Map;

import core.configs.GameConfig;
import core.configs.OptionsConfig;
import utils.Log;
import utils.exceptions.PowtakException;

public class SoundEngine {
	
	private OptionsConfig optionsConfig;
	
	// musics
	private AudioPlayer mainMusicAudioPlayer;
	private AudioPlayer endGameMusicAudioPlayer;
	
	private Map<Sound, AudioPlayer> audioPlayers;

	public SoundEngine(OptionsConfig optionsConfig) throws PowtakException {
		this.optionsConfig = optionsConfig;
		this.audioPlayers = new HashMap<>();
	}
	
	public void playHitSound() {
		play(Sound.HIT_SOUND, 0.0f);
	}
	
	public void playDeathSound() {
		play(Sound.DEATH_SOUND, 0.0f);
	}
	
	public void playDoorOpeningSound() {
		play(Sound.DOOR_OPENING_SOUND, 0.0f);
	}
	
	public void playMenuOptionSound() {
		play(Sound.MENU_OPTION_SOUND, 0.0f);
	}
	
	public void playMenuSelectionSound() {
		play(Sound.MENU_SELECTION_SOUND, 0.0f);
	}
	
	public void playPortalSound() {
		play(Sound.PORTAL_SOUND, 0.0f);
	}
	
	public void playPowerupSound() {
		play(Sound.POWERUP_SOUND, 0.0f);
	}
	
	public void playWallHitSound() {
		play(Sound.WALL_HIT_SOUND, 0.0f);
	}
	
	public void playMainMusic() {
		if(optionsConfig.isSoundActivated()) {
			try {
				this.mainMusicAudioPlayer = new AudioPlayer(Sound.MAIN_MUSIC, true, -20.0f);
				play(mainMusicAudioPlayer);
			} catch (PowtakException e) {
				e.printStackTrace();
			}
		} else {
			Log.warn("Main music is not playing as sound is deactivated");
		}
	}
	
	public void playEndGameMusic() {
		play(endGameMusicAudioPlayer);
	}
	
	public void stopMainMusic() throws PowtakException {
		stop(mainMusicAudioPlayer);
	}
	
	public void play(Sound sound, float volumeVariation) {
		play(sound, false, volumeVariation);
	}
	
	public void play(Sound sound, boolean loop, float volumeVariation) {
		if(optionsConfig.isSoundActivated()) {
			try {
				AudioPlayer audioPlayer = audioPlayers.get(sound);
				if(audioPlayer == null || (audioPlayer != null && !audioPlayer.isActivated())) {
					audioPlayer = new AudioPlayer(sound, loop, volumeVariation);
					audioPlayers.put(sound, audioPlayer);
					play(audioPlayer);
				}
			} catch (PowtakException e) {
				e.printStackTrace();
			}
		}
	}
	
	private final static int DEACTIVATION_FRAMES_COUNT = GameConfig.UPS * 10;
	
	public void play(AudioPlayer audioPlayer) {
		if(optionsConfig.isSoundActivated() && audioPlayer != null) {
			audioPlayer.start(DEACTIVATION_FRAMES_COUNT);
		}
	}
	
	public void stop(Sound sound) {
		if(optionsConfig.isSoundActivated()) {
			try {
				audioPlayers.get(sound).stop();
			} catch (PowtakException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		if(mainMusicAudioPlayer != null) {
			mainMusicAudioPlayer.update();
		}
		
		audioPlayers.values().forEach(audioPlayer -> audioPlayer.update());
		
		if(endGameMusicAudioPlayer != null) {
			endGameMusicAudioPlayer.update();
		}
	}
	
	public void stop(AudioPlayer audioPlayer) throws PowtakException {
		if(optionsConfig.isSoundActivated() && audioPlayer != null) {
			audioPlayer.stop();
		}
	}
	
	public void pause(AudioPlayer audioPlayer) {
		if(optionsConfig.isSoundActivated() && audioPlayer != null) {
			audioPlayer.pause();
		}
	}
	
	public void resume(AudioPlayer audioPlayer) throws PowtakException {
		if(optionsConfig.isSoundActivated() && audioPlayer != null) {
			audioPlayer.resume();
		}
	}
	
	public void restart(AudioPlayer audioPlayer) throws PowtakException {
		if(optionsConfig.isSoundActivated() && audioPlayer != null) {
			audioPlayer.restart();
		}
	}
	
}
