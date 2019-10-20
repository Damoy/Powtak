package core.entities.enemies;

import core.entities.Direction;
import core.world.Tile;
import core.world.level.Level;
import core.world.level.ingame.InGameLevel;
import rendering.Texture;

public abstract class Zombie extends Enemy {

	protected Direction direction;
	protected boolean inGameLevel;
	
	public Zombie(Level level, Tile tile, EnemyChunck enemyChunck, int x, int y, Texture texture, Direction initialDirection) {
		super(tile, enemyChunck, x, y, texture);
		this.direction = initialDirection;
		this.level = level;
		this.inGameLevel = (level instanceof InGameLevel);
	}
	
}
