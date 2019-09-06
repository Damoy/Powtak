package rendering.animation;

import rendering.Texture;

public class ExplosionAnimation extends BasicAnimationOnTick{

	public ExplosionAnimation(int delay, float scale) {
		super(delay, AnimationFrame.getFromData(Texture.PROJECTILE_SPRITESHEET, scale,
				1, 1, 1, 1,
				3, 0, 3, 3,
				6, 0, 3, 3));
	}

}
