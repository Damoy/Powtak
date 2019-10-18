package utils.exceptions;

public class NullObjectException extends PowtakException {

	private static final long serialVersionUID = 5912119935822322503L;

	public NullObjectException(String objectInformation) {
		super(formatMessage(objectInformation));
	}
	
	private static String formatMessage(String objectInformation) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("The object ");
		stringBuilder.append(objectInformation);
		stringBuilder.append(" is null.");
		return stringBuilder.toString();
	}

}
