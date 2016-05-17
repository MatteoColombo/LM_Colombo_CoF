package game.board;

public class NegativeException extends Exception {
	public NegativeException() {
		super();
	}

	public NegativeException(String errorMessage) {
		super(errorMessage);
	}
}
