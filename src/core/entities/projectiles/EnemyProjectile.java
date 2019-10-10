package core.entities.projectiles;

import core.entities.Direction;
import core.world.level.Level;
import rendering.animation.Animation;

public abstract class EnemyProjectile extends AnimatedProjectile{

	public EnemyProjectile(Level level, Direction direction, Animation animation, int x, int y) {
		super(level, direction, animation, x, y);
	}

}
