package server.model.configuration;

/**
 * An Exception that is thrown when there is an error with the loading of the
 * Configurations.
 * 
 * @author Matteo Colombo
 * @see Configuration
 * @see Exception
 */
public class ConfigurationErrorException extends Exception {

	private static final long serialVersionUID = -9160690740679203092L;

	/**
	 * Receives the error message and stores it.
	 * 
	 * @param errorMessage
	 *            the error message
	 * @see ConfigurationErrorException
	 */
	public ConfigurationErrorException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Throws an Exception for errors with the {@link Configuration
	 * Configurations}.
	 * 
	 * @param e
	 *            the Exception called
	 * @see ConfigurationErrorException
	 */
	public ConfigurationErrorException(Exception e) {
		super(e);
	}
}
