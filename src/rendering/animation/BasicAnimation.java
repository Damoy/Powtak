package rendering.animation;


import java.util.List;

import rendering.Screen;

public abstract class BasicAnimation extends Animation{

	protected List<AnimationFrame> frames;
	protected int framePtr;
	
	protected BasicAnimation(List<AnimationFrame> frames) {
		this.frames = frames;
		this.framePtr = 0;
	}
	
	public void render(Screen s, int x, int y) {
		frames.get(framePtr).render(s, x, y);
	}
	
	public AnimationFrame getCurrentFrame() {
		return frames.get(framePtr);
	}
	
}
