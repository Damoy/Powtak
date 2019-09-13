package rendering.animation;

import rendering.Screen;
import rendering.Texture;

public class StaticAnimation extends Animation {
	
	private AnimationFrame uniqueAnimationFrame;
	
	public StaticAnimation(Texture animationTexture, int textureStartX, int textureStartY,
			int textureWidth, int textureHeight, float textureScale) {
		uniqueAnimationFrame = new AnimationFrame(animationTexture, textureStartX, textureStartY, textureWidth, textureHeight, textureScale);
	}

	@Override
	public void render(Screen s, int x, int y) {
		uniqueAnimationFrame.render(s, x, y);
	}

	@Override
	public void update() {
	}

	@Override
	public AnimationFrame getCurrentFrame() {
		return uniqueAnimationFrame;
	}

}
