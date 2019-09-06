package core.entities.enemies;

import core.world.Tile;
import rendering.Screen;
import rendering.Texture;

public class Shadow extends Enemy{

	public Shadow(Tile tile, EnemyChunck enemyChunck, int x, int y) {
		super(tile, enemyChunck, x, y, Texture.SHADOW);
	}

	@Override
	public void render(Screen s) {
		s.renderTex(texture, x, y);
	}

	@Override
	public void update() {
		
	}

}
