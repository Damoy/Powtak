package core.entities;

import core.world.Tile;
import rendering.Screen;
import rendering.Texture;
import utils.AABB;
import utils.Config;

public abstract class Entity {

	protected int x;
	protected int y;
	protected Texture texture;
	protected Tile tile;
	
	public Entity(Texture texture) {
		this.texture = texture;
	}
	
	public Entity(Tile tile, int x, int y, Texture texture) {
		this.tile = tile;
		this.x = x;
		this.y = y;
		this.texture = texture;
	}
	
	public abstract void render(Screen s);
	public abstract void update();
	public abstract int getWidth();
	public abstract int getHeight();
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getRow() {
		return y / Config.TILE_SIZE;
	}
	
	public int getCol() {
		return x / Config.TILE_SIZE;
	}
	
	public Entity addX(int dx) {
		this.x += dx;
		return this;
	}
	
	public Entity addY(int dy) {
		this.y += dy;
		return this;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public AABB getBox() {
		return new AABB(x, y, getWidth(), getHeight());
	}
	
	public boolean collides(Entity entity) {
		return getBox().collides(entity.getBox());
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
}
