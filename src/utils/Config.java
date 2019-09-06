package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

public final class Config {

	private Config() {}
	
	public final static int UPS = 60;
	public final static int WIDTH = 272;
	public final static int HEIGHT = 176;
	public final static int SCALE = 4;
	public final static int S_WIDTH = WIDTH * SCALE;
	public final static int S_HEIGHT = HEIGHT * SCALE;
	public final static int TILE_SIZE = 16;
	public final static int NUM_ROWS = HEIGHT / TILE_SIZE;
	public final static int NUM_COLS = WIDTH / TILE_SIZE;
	public final static int TEXTURE_TYPE = BufferedImage.TYPE_INT_ARGB;
	public final static int DOOR_KEY_ENGINE_SIZE = 101;
	public final static int PLAYER_SPEED = TILE_SIZE >> 3;
	// public final static int PLAYER_ATTACK_SPEED_DELAY = 15; // frames
	
	public final static String TITLE = "Powtak";
	
	public final static boolean RESIZABLE = false;
	
	public final static Color GROUND_TILE_COLOR = Color.WHITE;
	public final static Color STANDARD_WALL_COLOR = new Color(173, 173, 173);
	public final static Color DESTRUCTIBLE_WALL_COLOR = new Color(73, 73, 73);
	public final static Color SPAWN_PLAYER_COLOR = Color.GREEN;
	public final static Color NEXT_LEVEL_TELEPORT_POINT_COLOR = Color.BLACK;
	public final static Color SHADOW_COLOR = Color.RED;
}
