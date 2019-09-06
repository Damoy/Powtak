package core.entities.player;

public class PlayerConfig {

	private int x;
	private int y;
	
	private PlayerConfig() {}
	
	private PlayerConfig(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static PlayerConfig empty() {
		return new PlayerConfig();
	}
	
	public static PlayerConfig of(int x, int y) {
		return new PlayerConfig(x, y);
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
}
