package model.exceptions;

public class EmporiumExistsException extends Exception {
	public EmporiumExistsException() {
		super();
	}

	public EmporiumExistsException(String errorMessage) {
		super(errorMessage);
	}
}