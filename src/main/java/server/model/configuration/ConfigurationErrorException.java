package server.model.configuration;

/**
 * This exception is generated when there is an error while loading the configuration
 * @author Matteo Colombo
 *
 */
public class ConfigurationErrorException extends Exception{
	
	private static final long serialVersionUID = -9160690740679203092L;

	public ConfigurationErrorException(String errorMessage){
		super(errorMessage);
	}
	
	public ConfigurationErrorException(Exception e){
		super(e);
	}
}
