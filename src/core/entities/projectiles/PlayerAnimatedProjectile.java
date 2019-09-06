package core.entities.projectiles;

import core.entities.Direction;
import core.entities.enemies.Enemy;
import core.entities.walls.Wall;
import core.world.Tile;
import core.world.level.Level;
import rendering.animation.Animation;
import utils.AABB;
import utils.Config;

public class PlayerAnimatedProjectile extends AnimatedProjectile{

	
	public PlayerAnimatedProjectile(Level level, Direction direction, Animation animation, int x, int y) {
		super(level, direction, animation, x, y);
	}

	@Override
	public void update() {
		AABB box = getBox();
		Enemy enemyCollision = level.enemyCollision(box);
		
		if(enemyCollision != null) {
			enemyCollision.die();
			die();
		} else {
			Wall wallCollision = level.wallCollision(box);
			if(wallCollision != null) {
				die();
				if(wallCollision.isDestructible()) {
					wallCollision.forget();
				}
			} else {
				Tile tile = getTile();
				if(tile == null || tile.isDoored()) {
					die();
					return;
				}
						
				x += dx;
				y += dy;
				
				if(x < 0 || x > Config.WIDTH || y < 0 || y > Config.HEIGHT) {
					die();
					return;
				}
				
				animation.update();
			}
		}
	}
	
}
