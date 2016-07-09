package server.model.configuration;

/**
 * This exception is generated when there is an error while loading the configuration
 * @author Matteo Colombo
 *
 */
public class ConfigurationErrorException extends Exception{
	
	private static final long serialVersionUID = -9160690740679203092L;

	/**
	 * Receives the error message which is used with the getMessage() method
	 * @param errorMessage
	 */
	public ConfigurationErrorException(String errorMessage){
		super(errorMessage);
	}
	
	/**
	 * This is used to rethrow another exception which generated a configuration error
	 * @param e
	 */
	public ConfigurationErrorException(Exception e){
		super(e);
	}
}
