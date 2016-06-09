package model.exceptions;

public class OverMaxValueException extends Exception {

	private static final long serialVersionUID = -4802858109880239703L;

	public OverMaxValueException() {
		super();
	}

	public OverMaxValueException(String errorMessage) {
		super(errorMessage);
	}
}
