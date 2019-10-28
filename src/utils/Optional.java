package utils;

public class Optional<T> {

	private T element;
	private boolean isNotNull;
	
	public Optional() {
		setupIsNotNull();
	}
	
	public Optional(T element) {
		this.element = element;
		setupIsNotNull();
	}
	
	private void setupIsNotNull() {
		this.isNotNull = this.element != null;
	}

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
		setupIsNotNull();
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
		setupIsNotNull();
	}
	
}
