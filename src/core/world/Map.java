package core.world;

import core.world.teleportation.NextLevelTpPoint;
import rendering.Screen;
import utils.Config;

public class Map {
	
	private int rows;
	private int cols;
	private Tile[][] tiles;
	
	public Map(int rows, int cols, Tile[][] tiles) {
		this.rows = rows;
		this.cols = cols;
		this.tiles = tiles;
	}
	
	public Map(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.tiles = new Tile[rows][cols];
	}
	
	public void update() {
		for(int row = 0; row < rows; ++row)
			for(int col = 0; col < cols; ++col)
				tiles[row][col].update();
	}
	
	public void render(Screen s) {
		for(int row = 0; row < rows; ++row)
			for(int col = 0; col < cols; ++col)
				tiles[row][col].render(s);
	}
	
	public Tile getTileAt(int row, int col) {
		return tiles[row][col];
	}
	
	public Tile getNormTileAt(int x, int y) {
		try {
			return tiles[y / Config.TILE_SIZE][x / Config.TILE_SIZE];
		} catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public boolean busy(int row, int col) {
		Tile t = getTileAt(row, col);
		return t.isDoored() || t.isKeyed() || t.isWalled();
	}
	
	public boolean normBusy(int x, int y) {
		Tile t = getNormTileAt(x, y);
		return t.isDoored() || t.isKeyed() || t.isWalled();
	}
	
	public void setNextLevelTpPoint(NextLevelTpPoint nextLevelTpPoint) {
		getNormTileAt(nextLevelTpPoint.getX(), nextLevelTpPoint.getY()).setNextLevelTpPoint(nextLevelTpPoint);
	}

	public int getNumRows() {
		return rows;
	}

	public int getNumCols() {
		return cols;
	}

}
