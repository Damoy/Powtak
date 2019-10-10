package utils;

public final class SharedMemory {

	private final static StringBuffer sharedStringBuffer = new StringBuffer();
	
	private SharedMemory() {}
	
	public static StringBuffer getStringBuffer() {
		return sharedStringBuffer;
	}
	
	public static void clearStringBuffer() {
		sharedStringBuffer.setLength(0);
	}
	
	public static StringBuffer getClearedStringBuffer() {
		clearStringBuffer();
		return sharedStringBuffer;
	}
}
