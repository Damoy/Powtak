package core.world.teleportation;

import core.world.level.Level;

public class NextLevelTpConfig {

	private int xDest;
	private int yDest;
	private Level levelDest;
	
	private NextLevelTpConfig() {}
	
	private NextLevelTpConfig(int xDest, int yDest, Level levelDest) {
		this.xDest = xDest;
		this.yDest = yDest;
		this.levelDest = levelDest;
	}
	
	public static NextLevelTpConfig empty() {
		return new NextLevelTpConfig();
	}
	
	public static NextLevelTpConfig of(int xDest, int yDest, Level levelDest) {
		return new NextLevelTpConfig(xDest, yDest, levelDest);
	}
	
	public static NextLevelTpConfig of(Level levelDest) {
		return new NextLevelTpConfig(levelDest.getPlayerConfig().getX(),
				levelDest.getPlayerConfig().getY(), levelDest);
	}

	public int getxDest() {
		return xDest;
	}

	public void setxDest(int xDest) {
		this.xDest = xDest;
	}

	public int getyDest() {
		return yDest;
	}

	public void setyDest(int yDest) {
		this.yDest = yDest;
	}

	public Level getLevelDest() {
		return levelDest;
	}

	public void setLevelDest(Level levelDest) {
		this.levelDest = levelDest;
	}
	
}
