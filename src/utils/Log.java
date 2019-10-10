package utils;

import java.time.LocalDateTime;

public final class Log {
	
	private Log() {}
	
	public static <T> void info(T o) {
		System.out.println(LocalDateTime.now() + " [INFO] " + o.toString());
	}
	
	public static <T> void warn(T o) {
		System.out.println(LocalDateTime.now() + "[WARN] " + o.toString());
	}
	
	public static <T> void error(T o) {
		System.out.println(LocalDateTime.now() + "[ERROR] " + o.toString());
	}
	
}
