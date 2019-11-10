package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import utils.Log;
import utils.SharedMemory;
import utils.TickCounter;
import utils.exceptions.GenericException;
import utils.exceptions.PowtakException;

public class AudioPlayer {

	private boolean activated = true;
	private Sound sound;
	private boolean loop;
	private File audioFile;
	private AudioPlayerStatus status;
	
	private AudioInputStream audioInputStream;
	private Clip clip;
	private long currentFrame;
	private float volumeVariation;
	
	private TickCounter activationCounter;
	
	public AudioPlayer(Sound sound, boolean loop, float volumeVariation) throws PowtakException {
		this.sound = sound;
		this.loop = loop;
		this.status = AudioPlayerStatus.STOPPED;
		this.volumeVariation = volumeVariation;
		try {
			this.createAudioInputStream();
			this.clip = AudioSystem.getClip();
			// this.clip.open(audioInputStream);
			// this.activateLoop();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			throw new GenericException(buildStartErrorMessage(), e.getMessage());
		}
	}
	
	private void createAudioInputStream() throws UnsupportedAudioFileException, IOException {
		this.audioFile = new File(sound.getFilePath());
		this.audioInputStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
	}
	
	public void activate() {
		setActivation(true);
	}
	
	public void deactivate(int frames) {
		setActivation(false);
		if(activationCounter == null && frames > 0) {
			activationCounter = new TickCounter(frames);
		}
	}
	
	public void update() {
		if(activationCounter != null) {
			if(activationCounter.isStopped()) {
				activationCounter = null;
				activate();
			} else {
				activationCounter.increment();
			}
		}
	}
	
	public void setActivation(boolean activated) {
		this.activated = activated;
	}
	
	public void start() {
		start(0);
	}
	
	public void start(int frames){
		if(!activated) {
			Log.warn("Audio player is not activated.");
			return;
		}
		
		if(status != AudioPlayerStatus.PLAYING) {
			try {
				clip.open(audioInputStream);
				this.activateLoop();
				if(volumeVariation != 0.0f) {
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(volumeVariation);
				}
				
				clip.start();
				status = AudioPlayerStatus.PLAYING;
			} catch (LineUnavailableException | IOException e) {
				e.printStackTrace();
			}
		} else {
			Log.warn("Audio player already playing");
		}
		
		if(frames > 0) {
			deactivate(frames);
		} else {
			setActivation(false);
		}
	}
	
	public void pause() {
		if(!activated) {
			Log.warn("Audio player is not activated.");
			return;
		}
		
		if(status == AudioPlayerStatus.PLAYING) {
			currentFrame = clip.getMicrosecondPosition();
			clip.stop();
			status = AudioPlayerStatus.PAUSED;
		} else {
			Log.warn("Audio player already paused");
		}
	}
	
	// TODO may bug
	public void resume() throws PowtakException {
		if(!activated) {
			Log.warn("Audio player is not activated.");
			return;
		}
		
		if(status == AudioPlayerStatus.PAUSED) {
			clip.close();
			resetAudioStream();
			clip.setMicrosecondPosition(currentFrame);
			start();
		} else {
			Log.warn("Cannot resume an audio player which is not paused");
		}
	}
	
	public void stop() throws PowtakException {
		if(!activated) {
			Log.warn("Audio player is not activated.");
			return;
		}
		
		if(status == AudioPlayerStatus.PLAYING) {
			reset();
			Log.debug("Audio player stopped");
		}
	}
	
	private void reset() throws PowtakException {
		status = AudioPlayerStatus.STOPPED;
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
	}
	
	public void restart() throws PowtakException {
		if(!activated) {
			Log.warn("Audio player is not activated.");
			return;
		}
		
		reset();
		start();
	}
	
	public void resetAudioStream() throws PowtakException {
		if(!activated) {
			Log.warn("Audio player is not activated.");
			return;
		}
		
		try {
			createAudioInputStream();
			// clip.open(audioInputStream);
			activateLoop();
		} catch (UnsupportedAudioFileException | IOException e) {
			throw new GenericException(buildStartErrorMessage(), e.getMessage());
		}
	}
	
	private String buildStartErrorMessage() {
		StringBuffer stringBuffer = SharedMemory.getClearedStringBuffer();
		stringBuffer.append("Could not start audio player with sound file ");
		stringBuffer.append(audioFile.toString());
		return stringBuffer.toString();
	}
	
	public void loop() {
		this.loop = true;
		activateLoop();
	}
	
	private void activateLoop() {
		if(loop && activated) {
			this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}
	
	public boolean isActivated() {
		return activated;
	}

	private static enum AudioPlayerStatus {
		PLAYING, PAUSED, STOPPED
	}
	
}
