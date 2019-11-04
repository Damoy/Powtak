package sound;

import javax.sound.sampled.Clip;

public class Sound {
	
	// sounds
	public final static Sound HIT_SOUND = new Sound("hit.wav");
	public final static Sound DEATH_SOUND = new Sound("death.wav");
	public final static Sound DOOR_OPENING_SOUND = new Sound("doorOpen.wav");
	public final static Sound MENU_OPTION_SOUND = new Sound("menuOption.wav");
	public final static Sound MENU_SELECTION_SOUND = new Sound("menuSelection.wav");
	public final static Sound PORTAL_SOUND = new Sound("portal.wav");
	public final static Sound POWERUP_SOUND = new Sound("powerup.wav");
	public final static Sound WALL_HIT_SOUND = new Sound("wallHit.wav");
	
	// musics
	public final static Sound MAIN_MUSIC = new Sound("main.wav");
	public final static Sound END_GAME_MUSIC = new Sound("endgame.wav");
	
	private final static String SOUND_BASE_PATH = "./resources/sounds/";
	private String filePath;
	private Clip clip;
	
	public Sound(String soundName) {
		this.filePath = buildSoundFilePath(soundName);
	}
	
	public void reset() {
		if(clip != null) {
			clip.stop();
			clip.setFramePosition(0);
		}
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static String buildSoundFilePath(String soundTitle) {
		return SOUND_BASE_PATH + soundTitle;
	}

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

}