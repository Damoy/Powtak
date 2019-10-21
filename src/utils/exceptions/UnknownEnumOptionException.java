package utils.exceptions;

public class UnknownEnumOptionException extends PowtakException {

	private static final long serialVersionUID = -4172842482745693549L;

	public UnknownEnumOptionException(String enumName, String option) {
		super(formatMessage(enumName, option));
	}
	
	private static String formatMessage(String enumName, String option) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("The option ");
		stringBuilder.append(option);
		stringBuilder.append(" is unknown for the enum ");
		stringBuilder.append(enumName);
		stringBuilder.append(".");
		return stringBuilder.toString();
	}

}
