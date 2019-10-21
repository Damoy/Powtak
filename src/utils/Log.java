package utils;

import java.time.LocalDateTime;

public final class Log {
	
	private Log() {}
	
	@SafeVarargs
	public static <T> void info(T o, T... os) {
		StringBuffer stringBuffer = SharedMemory.getClearedStringBuffer();
		stringBuffer.append(LocalDateTime.now());
		stringBuffer.append(" [INFO] ");
		stringBuffer.append(o.toString());
		for(int i = 0; i < os.length - 1; ++i) {
			stringBuffer.append(os[i].toString());
			stringBuffer.append(",");
		}
		stringBuffer.append(os[os.length - 1].toString());
		String s = stringBuffer.toString();
		System.out.println(s);
		SharedMemory.clearStringBuffer();
	}
	
	@SafeVarargs
	public static <T> void warn(T o, T... os) {
		StringBuffer stringBuffer = SharedMemory.getClearedStringBuffer();
		stringBuffer.append(LocalDateTime.now());
		stringBuffer.append(" [WARN] ");
		stringBuffer.append(o.toString());
		for(int i = 0; i < os.length - 1; ++i) {
			stringBuffer.append(os[i].toString());
			stringBuffer.append(",");
		}
		stringBuffer.append(os[os.length - 1].toString());
		String s = stringBuffer.toString();
		System.out.println(s);
		SharedMemory.clearStringBuffer();
	}
	
	@SafeVarargs
	public static <T> void error(T o, T... os) {
		StringBuffer stringBuffer = SharedMemory.getClearedStringBuffer();
		stringBuffer.append(LocalDateTime.now());
		stringBuffer.append(" [ERROR] ");
		stringBuffer.append(o.toString());
		for(int i = 0; i < os.length - 1; ++i) {
			stringBuffer.append(os[i].toString());
			stringBuffer.append(",");
		}
		stringBuffer.append(os[os.length - 1].toString());
		String s = stringBuffer.toString();
		System.out.println(s);
		SharedMemory.clearStringBuffer();
	}
	
	public static <T> void info(T o) {
		System.out.println(LocalDateTime.now() + " [INFO] " + o.toString());
	}
	
	public static <T> void warn(T o) {
		System.out.println(LocalDateTime.now() + " [WARN] " + o.toString());
	}
	
	public static <T> void error(T o) {
		System.out.println(LocalDateTime.now() + " [ERROR] " + o.toString());
	}
	
}
