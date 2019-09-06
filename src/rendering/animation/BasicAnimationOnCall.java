package rendering.animation;

import java.util.List;

import rendering.Texture;

public class BasicAnimationOnCall extends BasicAnimation{

	private int framesCount;
	private int ticksCap;
	private int ticksRef;
	
	protected BasicAnimationOnCall(int ticksCaps, List<AnimationFrame> frames) {
		super(frames);
		this.framesCount = frames.size();
		this.ticksCap = ticksCaps;
		this.ticksRef = 0;
	}
	
	public void update() {
		++ticksRef;
		if(ticksRef >= ticksCap) {
			ticksRef = 0;
			if(++framePtr >= framesCount)
				framePtr = 0;
		}
	}
	
	public AnimationFrame getCurrentFrame() {
		return frames.get(framePtr);
	}
	
	public static Animation get(Texture spritesheet, int ticksCap, float scale, int... framesData) {
		List<AnimationFrame> frames = AnimationFrame.getFromData(spritesheet, scale, framesData);
		return new BasicAnimationOnCall(ticksCap, frames);
	}
}
