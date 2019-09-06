package rendering.animation;

import java.util.HashMap;
import java.util.Map;

import core.entities.Direction;
import utils.IntegerRef;

public class DirectedAnimationOnCall extends DirectedAnimation{

	private Map<Direction, IntegerRef> tickCounters;
	private Map<Direction, Integer> tickCaps;
	private boolean directionSet;
	
	public DirectedAnimationOnCall() {
		this.tickCounters = new HashMap<>();
		this.tickCaps = new HashMap<>();
		tickCounters.values().forEach(counter -> counter.set(0));
		this.directionSet = false;
	}
	
	@Override
	public void addDirTimeEntry(Direction direction, int ticks) {
		tickCaps.put(direction, ticks);
	}

	@Override
	public void update() {
		if(!directionSet) throw new IllegalStateException();
		
		IntegerRef ref = tickCounters.get(direction);
		ref.inc(1);
		
		if(ref.get() >= tickCaps.get(direction)) {
			ref.set(0);
		}
		
		currentFrame = frames.get(direction).get(ref.get());
		directionSet = false;
	}
	
	public void prepareUpdate(Direction direction) {
		this.direction = direction;
	}
	
}
