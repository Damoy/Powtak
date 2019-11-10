package utils.exceptions;

import utils.SharedMemory;

public class GenericException extends PowtakException {

	private static final long serialVersionUID = -2592584630129860774L;

	public GenericException(String msg, String... additionalMessages) {
		super(buildMessage(msg, additionalMessages));
	}
	
	private static String buildMessage(String msg, String... msgs) {
		StringBuffer stringBuffer = SharedMemory.getClearedStringBuffer();
		stringBuffer.append(msg);
		stringBuffer.append("\n");
		for(int i = 0; i < msgs.length - 1; ++i) {
			stringBuffer.append(msgs[i]);
			stringBuffer.append("\n");
		}
		stringBuffer.append(msgs[msgs.length - 1]);
		return stringBuffer.toString();
	}

}
