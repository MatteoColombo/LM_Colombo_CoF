package server.model.board.map;

/**
 * An Exception that is thrown when there is an error with the Map XML file.
 * 
 * @see Exception
 * @see MapLoader
 */
public class MapXMLFileException extends Exception {

	private static final long serialVersionUID = -134490452064848769L;

	/**
	 * Sets the Exception which generated this Map configuration error.
	 * 
	 * @param e
	 *            the Exception called
	 */
	public MapXMLFileException(Exception e) {
		super(e);
	}

}
