package core.entities.player;

import core.entities.Direction;

public class PlayerConfig {

	private int x;
	private int y;
	private int energy;
	private Direction direction;
	
	public PlayerConfig() {}
	
	public PlayerConfig(int x, int y, int energy, Direction direction) {
		this.x = x;
		this.y = y;
		this.energy = energy;
		this.direction = direction;
	}
	
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
	
}
