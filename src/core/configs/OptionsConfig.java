package core.configs;

public class OptionsConfig {

	private boolean soundActivated;
	private CharacterSkin characterSkin;
	
	public OptionsConfig() {
		this.soundActivated = true;
		this.characterSkin = CharacterSkin.STANDARD;
	}

	public OptionsConfig(boolean soundActivated, CharacterSkin characterSkin) {
		this.soundActivated = soundActivated;
		this.characterSkin = characterSkin;
	}

	public boolean isSoundActivated() {
		return soundActivated;
	}

	public void setSoundActivated(boolean soundActivated) {
		this.soundActivated = soundActivated;
	}

	public CharacterSkin getCharacterSkin() {
		return characterSkin;
	}

	public void setCharacterSkin(CharacterSkin characterSkin) {
		this.characterSkin = characterSkin;
	}

}
