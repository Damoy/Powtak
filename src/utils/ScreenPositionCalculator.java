package utils;

import java.awt.Point;

public class ScreenPositionCalculator {

	private ScreenPositionCalculator() {}
	
	public static Point getScreenUIPositionStart() {
		return new Point((int) (Config.TILE_SIZE * 0.1f), Config.HEIGHT - (Config.TILE_SIZE >> 2));
	}
	
	public static Point getScreenUIPositionEnd() {
		return new Point(Config.WIDTH - (Config.TILE_SIZE >> 1), Config.TILE_SIZE >> 2);
	}
	
	public static int getRowOffset(int nb) {
		return Config.TILE_SIZE * nb;
	}
	
	public static int getColOffset(int nb) {
		return Config.TILE_SIZE * nb;
	}
	
	public static Point getCenter() {
		return new Point(Config.WIDTH >> 1, Config.HEIGHT >> 1);
	}
	
}
