package core.entities.projectiles;

import core.entities.Direction;
import core.entities.walls.Wall;
import core.world.Tile;
import core.world.level.Level;
import rendering.Screen;
import rendering.animation.Animation;
import utils.AABB;
import utils.Config;

public class ZombieProjectile extends EnemyProjectile{

	public ZombieProjectile(Level level, Direction direction, Animation animation, int x, int y) {
		super(level, direction, animation, x, y);
	}

	@Override
	public void update() {
		AABB box = getBox();
		Wall wallCollision = level.wallCollision(box);
		
		if(wallCollision != null) {
			die();
			if(wallCollision.isDestructible()) {
				wallCollision.forget();
			}
		} else {
			
			if(level.playerCollidesWith(this)) {
				die();
				level.getPlayer().die();
				return;
			}
			
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
	
	@Override
	public void render(Screen s) {
		animation.getCurrentFrame().render(s, x, y);
	}

}
