package core.entities.player;

public class PlayerConfig {

	private int x;
	private int y;
	private int energy;
	
	public PlayerConfig() {}
	
	public PlayerConfig(int x, int y, int energy) {
		this.x = x;
		this.y = y;
		this.energy = energy;
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
	
}
