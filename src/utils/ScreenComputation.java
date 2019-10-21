package utils;

import java.awt.Point;

import core.configs.GameConfig;

public class ScreenComputation {

	private ScreenComputation() {}
	
	public static Point getScreenUIPositionStart() {
		return new Point((int) (GameConfig.TILE_SIZE * 0.1f), GameConfig.HEIGHT - (GameConfig.TILE_SIZE >> 2));
	}
	
	public static Point getScreenUIPositionEnd() {
		return new Point(GameConfig.WIDTH - (GameConfig.TILE_SIZE >> 1), GameConfig.TILE_SIZE >> 2);
	}
	
	public static int getRowOffset(int nb) {
		return GameConfig.TILE_SIZE * nb;
	}
	
	public static int getColOffset(int nb) {
		return GameConfig.TILE_SIZE * nb;
	}
	
	public static Point getCenter() {
		return new Point(GameConfig.WIDTH >> 1, GameConfig.HEIGHT >> 1);
	}
	
	public static Point computePositionAtUIStart(int content, float fontSize, int tileOffset) {
		int ifontSize = (int) fontSize;
		int digitsContent = Utils.getDigitsLen(content);
		int digitsFontSize = Utils.getDigitsLen(ifontSize);
		
		Point pos = getScreenUIPositionStart();
		int x = pos.x + tileOffset * GameConfig.TILE_SIZE;
		int xoffset = (3 - digitsFontSize);
		x += (digitsContent >= 2) ? -1: (4 - digitsContent);
		x += xoffset;
		return new Point(x, pos.y);
	}
	
}
