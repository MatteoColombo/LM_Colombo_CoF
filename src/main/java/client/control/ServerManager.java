package client.control;

import java.io.IOException;

/**
 * This is the interface that the class which manages the connections to the server need to implement
 */
public interface ServerManager {
	/**
	 * send a message to the server to be parsed
	 * @param message the String to send. 
	 * This must respect some format rules, depending on the situation
	 * @throws IOException if the connection fails
	 */
	public void publishMessage(String message) throws IOException;
	
	/**
	 * This is the method which is called when the client wants to disconnect
	 */
	public void disconnect();
}	
