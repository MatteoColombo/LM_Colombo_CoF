package model.exceptions;

public class XMLFileException extends Exception {
	
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
