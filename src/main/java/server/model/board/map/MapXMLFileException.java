package server.model.board.map;

/**
 * An Exception that is thrown when there is an error with the XML file of the
 * Map.
 * 
 * @see Exception
 * @see MapLoader
 */
public class MapXMLFileException extends Exception {

	private static final long serialVersionUID = -134490452064848769L;

	/**
	 * Throws an Exception for errors with the XML file of the Map.
	 * 
	 * @param e
	 *            the Exception called
	 * @see MapXMLFileException
	 */
	public MapXMLFileException(Exception e) {
		super(e);
	}

}
