package core.entities.walls;

import core.entities.Entity;
import core.world.Tile;
import rendering.Texture;

public abstract class Wall extends Entity{

	protected WallChunck wallChunk;
	
	public Wall(Tile tile, WallChunck wallChunk, int x, int y, Texture texture) {
		super(tile, x, y, texture);
	}
	
	public abstract boolean isDestructible();
	
	@Override
	public int getWidth() {
		return texture.getWidth();
	}
	
	@Override
	public int getHeight() {
		return texture.getHeight();
	}
	
	public void forget() {
		wallChunk.remove(this);
		tile.setWall(null);
	}
	
	public void setWallChunck(WallChunck wallChunck) {
		this.wallChunk = wallChunck;
	}
}
