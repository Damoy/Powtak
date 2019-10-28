package core.entities.player;

import core.configs.CharacterSkin;
import core.entities.Direction;

public class PlayerConfig {

	private int x;
	private int y;
	private int energy;
	private Direction direction;
	private CharacterSkin characterSkin;
	
	public PlayerConfig() {}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public CharacterSkin getCharacterSkin() {
		return characterSkin;
	}

	public void setCharacterSkin(CharacterSkin characterSkin) {
		this.characterSkin = characterSkin;
	}
	
}
