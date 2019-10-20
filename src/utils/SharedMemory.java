package utils;

public final class SharedMemory {

	private final static StringBuffer sharedStringBuffer = new StringBuffer();
	
	private SharedMemory() {}
	
	public synchronized static StringBuffer getStringBuffer() {
		return sharedStringBuffer;
	}
	
	public synchronized static void clearStringBuffer() {
		sharedStringBuffer.setLength(0);
	}
	
	public synchronized static StringBuffer getClearedStringBuffer() {
		clearStringBuffer();
		return sharedStringBuffer;
	}
}
