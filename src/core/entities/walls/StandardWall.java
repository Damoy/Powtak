package core.entities.walls;
import core.world.Tile;
import rendering.Screen;
import rendering.Texture;

public class StandardWall extends Wall{

	public StandardWall() {
		super(null, null, 0, 0, Texture.STANDARD_WALL);
	}
	
	public StandardWall(Tile tile, WallChunck wallChunck, int x, int y) {
		super(tile, wallChunck, x, y, Texture.STANDARD_WALL);
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
		return false;
	}

}
