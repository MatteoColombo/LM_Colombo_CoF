package client.control;

import java.io.IOException;

@FunctionalInterface
public interface ServerManager {
	/**
	 * send a message to the server to be parsed
	 * @param message the String to send. 
	 * This must respect some format rules, depending on the situation
	 * @throws IOException if the connection fails
	 */
	public void publishMessage(String message) throws IOException;
}	
