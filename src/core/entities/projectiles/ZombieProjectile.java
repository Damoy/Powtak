package core.entities.projectiles;

import core.configs.GameConfig;
import core.entities.Direction;
import core.entities.walls.Wall;
import core.world.Tile;
import core.world.level.Level;
import core.world.level.ingame.InGameLevel;
import rendering.Screen;
import rendering.animation.Animation;
import utils.AABB;
import utils.Log;

public class ZombieProjectile extends EnemyProjectile{
	
	private boolean inGameLevel;
	
	public ZombieProjectile(Level level, boolean inGameLevel, Direction direction, Animation animation, int x, int y) {
		super(level, direction, animation, x, y);
		this.inGameLevel = inGameLevel;
	}

	@Override
	public void update() {
		AABB box = getBox();
		Wall wallCollision = level.wallCollision(box);
		
		if(wallCollision != null) {
			die();
		} else {
			if(inGameLevel) {
				InGameLevel level = (InGameLevel) this.level;
				if(level.playerCollidesWith(this)) {
					die();
					level.getPlayer().die();
					Log.warn("DIE");
					return;
				}
			} 
			
			Tile tile = getTile();
			if(tile == null || tile.isDoored()) {
				die();
				return;
			}
					
			x += dx;
			y += dy;
			
			if(x < 0 || x > GameConfig.WIDTH || y < 0 || y > GameConfig.HEIGHT) {
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
