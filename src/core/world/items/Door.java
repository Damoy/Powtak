package core.world.items;

import core.entities.Entity;
import core.world.Tile;
import rendering.Screen;
import rendering.Texture;
import utils.Config;

public class Door extends Entity{
	
	private int id;

	public Door(Tile tile, int id, int x, int y) {
		super(tile, x, y, Texture.DOOR);
		this.id = Config.getDoorId(id);
	}

	@Override
	public void render(Screen s) {
		s.renderTex(texture, x, y);
	}

	@Override
	public void update() {
		
	}

	public int getId() {
		return id;
	}
	
	public void interact() {
		tile.setDoor(null);
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
