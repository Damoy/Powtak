package core.entities.projectiles;

import core.entities.Direction;
import core.world.level.Level;
import rendering.Screen;
import rendering.animation.Animation;

public abstract class AnimatedProjectile extends Projectile{

	protected Animation animation;
	
	public AnimatedProjectile(Level level, Direction direction, Animation animation, int x, int y) {
		super(level, direction, x, y);
		this.animation = animation;
	}
	
	@Override
	public int getWidth() {
		return animation.getCurrentFrame().getWidth();
	}

	@Override
	public int getHeight() {
		return animation.getCurrentFrame().getHeight();
	}
	
	@Override
	public void render(Screen s) {
		animation.getCurrentFrame().render(s, x, y);
	}
	
	public Animation getAnimation() {
		return animation;
	}

}
