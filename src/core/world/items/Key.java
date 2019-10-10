package core.world.items;

import core.entities.Entity;
import core.world.Tile;
import rendering.Screen;
import rendering.Texture;
import utils.Config;

public class Key extends Entity{

	private int id;
	
	public Key(Tile tile, int id, int x, int y) {
		super(tile, x, y, Texture.KEY);
		this.id = id;
	}

	@Override
	public void render(Screen s) {
		s.renderTex(texture, x, y);
	}

	@Override
	public void update() {
		
	}
	
	public void interact() {
		tile.setKey(null);
	}
	
	public int getId() {
		return id;
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
