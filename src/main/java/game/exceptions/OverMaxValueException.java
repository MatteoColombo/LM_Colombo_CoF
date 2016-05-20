package game.exceptions;

public class OverMaxValueException extends Exception {
	public OverMaxValueException() {
		super();
	}

	public OverMaxValueException(String errorMessage) {
		super(errorMessage);
	}
}