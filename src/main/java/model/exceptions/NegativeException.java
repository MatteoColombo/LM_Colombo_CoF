package model.exceptions;

public class NegativeException extends Exception {
	
	private static final long serialVersionUID = 2533291318333659126L;

	public NegativeException() {
		super();
	}

	public NegativeException(String errorMessage) {
		super(errorMessage);
	}
}
