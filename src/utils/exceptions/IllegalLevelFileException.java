package utils.exceptions;

public class IllegalLevelFileException extends PowtakException {

	private static final long serialVersionUID = -3192484211141054568L;

	public IllegalLevelFileException(String levelFileName) {
		super(formatMessage(levelFileName));
	}
	
	private static String formatMessage(String levelFileName) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("The level file ");
		stringBuilder.append(levelFileName);
		stringBuilder.append(" is badly formed.");
		return stringBuilder.toString();
	}

}
