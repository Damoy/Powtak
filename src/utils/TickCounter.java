package utils;


public class TickCounter {

	private int bound;
	private int tick;
	private boolean stopped;
	
	public TickCounter(int bound){
		tick = 0;
		stopped = false;
		this.bound = bound;
	}
	
	public boolean increment(){
		if(stopped) return true;
		tick++;
		if(tick >= bound) {
			tick = bound;
			stopped = true;
		}
		
		return stopped;
	}

	public boolean isStopped() {
		return stopped;
	}
	
	public void reset(){
		tick = 0;
		stopped = false;
	}
}
