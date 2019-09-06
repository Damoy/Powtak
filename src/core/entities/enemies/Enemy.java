package core.entities.enemies;

import core.entities.Entity;
import core.world.Tile;
import core.world.level.Level;
import rendering.Texture;

public abstract class Enemy extends Entity {

	protected EnemyChunck enemyChunck;
	protected Level level;
	
	public Enemy(Tile tile, EnemyChunck enemyChunck, int x, int y, Texture texture) {
		super(tile, x, y, texture);
		this.enemyChunck = enemyChunck;
		this.level = null;
	}
	
	@Override
	public int getWidth() {
		return texture.getWidth();
	}
	
	@Override
	public int getHeight() {
		return texture.getHeight();
	}
	
	public void die() {
		enemyChunck.remove(this);
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level, boolean force) {
		if(level != null || this.level == null || this.level != null && force) {
			this.level = level;
		}
	}

}
