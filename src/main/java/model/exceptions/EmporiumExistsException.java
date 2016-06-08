package model.exceptions;

public class EmporiumExistsException extends Exception {

	private static final long serialVersionUID = 850242798477448190L;

	public EmporiumExistsException() {
		super();
	}

	public EmporiumExistsException(String errorMessage) {
		super(errorMessage);
	}
}
