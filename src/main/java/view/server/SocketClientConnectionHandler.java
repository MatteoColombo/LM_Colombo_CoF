package view.server;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.Server;

/**
 * This class is the one which is used to handle a new Socket connection to the server
 * @author Matteo Colombo
 *
 */
public class SocketClientConnectionHandler extends Thread {
	
	private Socket clientSocket;
	private Logger logger= Logger.getGlobal();
	
	public SocketClientConnectionHandler(Socket clientSocket){
		this.clientSocket=clientSocket;
	}
	
	/**
	 * This is the thread which manages the client's login.
	 * It generates a new socketclient and then he logs them into the server.
	 * This thread also will manage the game creation, if the client is the first of a game
	 */
	@Override
	public void run(){
		try {
			ClientInt client= new SocketClient(clientSocket);
			client.askPlayerName();
			Server.login(client);
		} catch (IOException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}
	}
}
