package server.model.configuration;

/**
 * This Exception is generated when there is an error while loading the nobility
 * track
 * 
 * @author Matteo Colombo
 *
 */
public class TrackXMLFileException extends Exception {

	private static final long serialVersionUID = -733823221846521326L;

	/**
	 * This is used to rethrow an exception which generated an error with the XML file of the nobility track
	 * @param e
	 */
	public TrackXMLFileException(Exception e) {
		super(e);
	}
}
