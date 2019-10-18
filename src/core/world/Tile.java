package core.world;

import core.entities.Entity;
import core.entities.items.Energy;
import core.entities.walls.Wall;
import core.world.items.Door;
import core.world.items.Key;
import core.world.teleportation.Portal;
import rendering.Screen;
import rendering.Texture;

public class Tile extends Entity{

	private Key key;
	private Door door;
	private Wall wall;
	private Portal nextLevelPortal;
	private Energy energy;
	
	public Tile(int x, int y, Texture texture) {
		super(null, x, y, texture);
		key = null;
		door = null;
		wall = null;
		nextLevelPortal = null;
		tile = this;
		energy = null;
	}

	@Override
	public void render(Screen s) {
		s.renderTex(texture, x, y);
	}

	@Override
	public void update() {
	}

	public boolean isWalled() {
		return wall != null;
	}

	public boolean isDoored() {
		return door != null;
	}

	public boolean isKeyed() {
		return key != null;
	}
	
	public boolean isPoweredUp() {
		return energy != null;
	}
	
	public boolean isNextLevelPortaled() {
		return nextLevelPortal != null;
	}
	
	public Key getKey() {
		return key;
	}

	public Door getDoor() {
		return door;
	}

	public Wall getWall() {
		return wall;
	}

	public Portal getNextLevelPortal() {
		return nextLevelPortal;
	}

	public Energy getEnergy() {
		return energy;
	}
	
	@Override
	public int getWidth() {
		return texture.getWidth();
	}
	
	@Override
	public int getHeight() {
		return texture.getHeight();
	}
	
	public void setKey(Key key) {
		this.key = key;
	}

	public void setDoor(Door door) {
		this.door = door;
	}

	public void setWall(Wall wall) {
		this.wall = wall;
	}

	public void setNextLevelPortal(Portal nextLevelPortal) {
		this.nextLevelPortal = nextLevelPortal;
	}

	public void setEnergy(Energy energy) {
		this.energy = energy;
	}

}
