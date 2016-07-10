package server.model.board.nobility;

/**
 * An Exception that is thrown when there is an error with the XML file of the
 * NobilityTrack.
 * 
 * @author Matteo Colombo
 * @see Exception
 * @see NobilityLoader
 *
 */
public class TrackXMLFileException extends Exception {

	private static final long serialVersionUID = -733823221846521326L;

	/**
	 * Throws an Exception for errors with the XML file of the
	 * {@link NobilityTrack}.
	 * 
	 * @param e
	 *            the Exception called
	 * @see TrackXMLFileException
	 */
	public TrackXMLFileException(Exception e) {
		super(e);
	}
}
