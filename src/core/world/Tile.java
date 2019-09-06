package core.world;

import core.entities.Entity;
import core.entities.items.Energy;
import core.entities.walls.Wall;
import core.world.items.Door;
import core.world.items.Key;
import core.world.teleportation.NextLevelTpPoint;
import rendering.Screen;
import rendering.Texture;

public class Tile extends Entity{

	private Key key;
	private Door door;
	private Wall wall;
	private NextLevelTpPoint nextLevelTpPoint;
	private Energy energy;
	
	public Tile(int x, int y, Texture texture) {
		super(null, x, y, texture);
		key = null;
		door = null;
		wall = null;
		nextLevelTpPoint = null;
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
	
	public boolean isNextLevelTpPointed() {
		return nextLevelTpPoint != null;
	}
	
	public boolean isPoweredUp() {
		return energy != null;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Door getDoor() {
		return door;
	}

	public void setDoor(Door door) {
		this.door = door;
	}

	public Wall getWall() {
		return wall;
	}

	public void setWall(Wall wall) {
		this.wall = wall;
	}
	
	public void setNextLevelTpPoint(NextLevelTpPoint nextLevelTpPoint) {
		this.nextLevelTpPoint = nextLevelTpPoint;
	}

	public NextLevelTpPoint getNextLevelTpPoint() {
		return nextLevelTpPoint;
	}
	
	public Energy getEnergy() {
		return energy;
	}

	public void setEnergy(Energy energy) {
		this.energy = energy;
	}

	@Override
	public int getWidth() {
		return texture.getWidth();
	}
	
	@Override
	public int getHeight() {
		return texture.getHeight();
	}
	
}
