package rendering.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.entities.Direction;
import utils.TickCounter;

public class DirectedAnimationOnTick extends DirectedAnimation{

	private Map<Direction, TickCounter> tickCounters;
	private List<AnimationFrame> frameChunck;
	
	private TickCounter tickCounter;
	private int frameCount;
	private int frameGroupPtr;
	
	public DirectedAnimationOnTick() {
		super();
		this.tickCounters = new HashMap<>();
		this.frameChunck = new ArrayList<>();
		this.tickCounter = null;
		this.frameCount = 0;
		this.frameGroupPtr = 0;
	}
	
	@Override
	public void update() {
		tickCounter.increment();
		
		if(tickCounter.isStopped()) {
			++frameGroupPtr;
			tickCounter.reset();
			if(frameGroupPtr >= frameCount) {
				frameGroupPtr = 0;
			}
		}
		
		this.currentFrame = frameChunck.get(frameGroupPtr);
	}

	@Override
	public void setDirection(Direction direction) {
		if(direction != this.direction) {
			frameGroupPtr = 0;
			this.direction = direction;
			frameChunck = frames.get(direction);
			frameCount = frameChunck.size();
			tickCounter = tickCounters.get(direction);
			this.currentFrame = frameChunck.get(frameGroupPtr);
		}
	}
	
	@Override
	public void addDirTimeEntry(Direction direction, int ticks) {
		tickCounters.put(direction, new TickCounter(ticks));
	}
	
}
