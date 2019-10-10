package core.entities.walls;

import core.world.Tile;
import rendering.Screen;
import rendering.Texture;

public class DestructibleWall extends Wall{

	public DestructibleWall() {
		super(null, null, 0, 0, Texture.DESTRUCTIBLE_WALL);
	}
	
	public DestructibleWall(Tile tile, WallChunck wallChunck, int x, int y) {
		super(tile, wallChunck, x, y, Texture.DESTRUCTIBLE_WALL);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Screen s) {
		s.renderTex(texture, x, y);
	}

	@Override
	public boolean isDestructible() {
		return true;
	}
}
