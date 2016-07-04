package server.control.connection.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.Server;
import server.control.connection.ClientInt;

/**
 * This class is the one which is used to handle a new Socket connection to the server
 * @author Matteo Colombo
 *
 */
public class SocketClientConnectionHandler extends Thread {
	
	private Socket clientSocket;
	private Logger logger= Logger.getGlobal();
	private int timeout;
	
	/**
	 * Saves the socket and the timeout which need to be passed to the client interfaces
	 * @param clientSocket
	 * @param timeout
	 */
	public SocketClientConnectionHandler(Socket clientSocket, int timeout){
		this.clientSocket=clientSocket;
		this.timeout=timeout;
	}
	
	/**
	 * This is the thread which manages the client's login.
	 * It generates a new socketclient and then he logs them into the server.
	 * This thread also will manage the game creation, if the client is the first of a game
	 */
	@Override
	public void run(){
		try {
			ClientInt client= new SocketClient(clientSocket, timeout);
			client.askPlayerName();
			Server.login(client);
		} catch (IOException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}
	}
}
