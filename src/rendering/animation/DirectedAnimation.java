package rendering.animation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.entities.Direction;
import rendering.Screen;

public abstract class DirectedAnimation extends Animation {

	protected Map<Direction, List<AnimationFrame>> frames;
	protected AnimationFrame currentFrame;
	protected Direction direction;

	public DirectedAnimation() {
		this.frames = new HashMap<>();
		this.currentFrame = null;
		this.direction = Direction.NULL;
	}

	public abstract void addDirTimeEntry(Direction direction, int ticks);

	public void render(Screen s, int x, int y) {
		currentFrame.render(s, x, y);
	}

	public void addAnimationChunck(Direction direction, List<AnimationFrame> frames) {
		this.frames.put(direction, frames);
	}

	public AnimationFrame getCurrentFrame() {
		return currentFrame;
	}

	public Direction getCurrentDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
