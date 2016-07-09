package server.model.board.map;

/**
 * This is the exception which is generated when there is an error with the map
 * XML file
 */
public class MapXMLFileException extends Exception {

	private static final long serialVersionUID = -134490452064848769L;

	/**
	 * Sets the exception which generated the map configuration error
	 * 
	 * @param e
	 */
	public MapXMLFileException(Exception e) {
		super(e);
	}

}
