package utils;

public final class Logger {

	private Logger() {}
	
	public static <T> void log(T o) {
		System.out.print(o.toString());
	}
	
	public static <T> void logn(T o) {
		System.out.println(o.toString());
	}
}
