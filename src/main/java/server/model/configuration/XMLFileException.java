package server.model.configuration;

import server.model.board.map.MapXMLFileException;

/**
 * This is the exception which indicates that there was a problem with one of the XML files
 * @author Matteo Colombo
 *
 */
public class XMLFileException extends Exception {
	
	private static final long serialVersionUID = 6160929647360037079L;
	private final String error;
	
	/**
	 * This is used to rethrow errors found while loading the nobility track
	 * @param e
	 */
	public XMLFileException(TrackXMLFileException e){
		super(e);
		error = "Error while loading the XML file of the nobility track";
	}
	
	/**
	 * This is used to rethrow errors found while loading the map
	 * @param e
	 */
	public XMLFileException(MapXMLFileException e){
		super(e);
		error = "Error while loading the XML file of the map";
	}
	
	/**
	 * This method returns a string which is the error
	 */
	@Override
	public String getMessage(){
		return error;
	}
	
	
}
