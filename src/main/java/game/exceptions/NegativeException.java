package game.exceptions;

public class NegativeException extends Exception {
	public NegativeException() {
		super();
	}

	public NegativeException(String errorMessage) {
		super(errorMessage);
	}
}
