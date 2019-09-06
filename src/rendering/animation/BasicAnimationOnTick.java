package rendering.animation;

import java.util.List;

import rendering.Texture;
import utils.TickCounter;

public class BasicAnimationOnTick extends BasicAnimation{

	private TickCounter tickCounter;
	private int framesCount;
	
	protected BasicAnimationOnTick(int delay, List<AnimationFrame> frames) {
		super(frames);
		this.framesCount = frames.size();
		this.tickCounter = new TickCounter(delay);
	}

	@Override
	public void update() {
		tickCounter.increment();
		if(tickCounter.isStopped()) {
			++framePtr;
			tickCounter.reset();
		}
		
		if(framePtr >= framesCount)
			framePtr = 0;
	}
	
	public static Animation get(Texture spritesheet, int delay, float scale, int... framesData) {
		List<AnimationFrame> frames = AnimationFrame.getFromData(spritesheet, scale, framesData);
		return new BasicAnimationOnTick(delay, frames);
	}
}
