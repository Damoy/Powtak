package rendering.animation;

import rendering.Screen;

public abstract class Animation {

	public abstract void render(Screen s, int x, int y);
	public abstract void update();
	public abstract AnimationFrame getCurrentFrame();
	
}
