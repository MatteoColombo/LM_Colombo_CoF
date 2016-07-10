package server.model.configuration;

import server.model.board.map.MapLoader;
import server.model.board.map.MapXMLFileException;
import server.model.board.nobility.NobilityTrack;
import server.model.board.nobility.TrackXMLFileException;

/**
 * An Exception that is thrown when there is an error with the loading of a XML
 * file.
 * 
 * @author Matteo Colombo
 * @see Exception
 */
public class XMLFileException extends Exception {

	private static final long serialVersionUID = 6160929647360037079L;
	private final String error;

	/**
	 * Throws an Exception for errors found while loading the
	 * {@link NobilityTrack} from its XML file.
	 * 
	 * @param e
	 *            the Exception called
	 * @see XMLFileException
	 */
	public XMLFileException(TrackXMLFileException e) {
		super(e);
		error = "Error while loading the XML file of the nobility track";
	}

	/**
	 * Throws an Exception for errors found while loading the {@link MapLoader
	 * Map} from its XML file.
	 * 
	 * @param e
	 *            the Exception called
	 * @see XMLFileException
	 */
	public XMLFileException(MapXMLFileException e) {
		super(e);
		error = "Error while loading the XML file of the map";
	}

	@Override
	public String getMessage() {
		return error;
	}

}
