package server;

import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import control.Controller;
import model.Game;
import model.exceptions.ConfigurationErrorException;

public class Server{
	private static List<Game> games;
	private static List<Controller> controllers;
	private static final String NAME = "ServerInt";
	private static Logger logger = Logger.getGlobal();
	private static final int socketPort = 2344;
	private static ServerSocket socketServer;

	public static synchronized void login(ClientInt client) {
		if (games.isEmpty() || games.get(0).isComplete()) {
			Game newGame;
			try {
				newGame = new Game();
			} catch (ConfigurationErrorException e) {
				e.printStackTrace();
				return;
			}
			newGame.addPlayer(client);
			games.add(0, newGame);
			controllers.add(0,new Controller(newGame));
			client.setController(controllers.get(0));
		} else {
			games.get(0).addPlayer(client);
			if(games.get(0).getPlayersNumber()==2){
				new InitializationTimeLimitManager(games.get(0));
			}
			client.setController(controllers.get(0));
			if(games.get(0).isComplete())
				games.get(0).start();
		}
		return;
	}

	public static void main(String[] args) {
		games= new ArrayList<>();
		controllers= new ArrayList<>();
		try {
			ServerInt room = new RMIServer();
			ServerInt stub = (ServerInt) UnicastRemoteObject.exportObject(room, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind(NAME, stub);
			logger.info("RMI server ready");
			socketServer= new ServerSocket(socketPort);
			logger.info("Socket server ready");
			while(true){
				new SocketClientConnectionHandler(socketServer.accept());
			}
		} catch (Exception e) {
			System.err.println("Chat Server exception:");
			e.printStackTrace();
		}
	}
	

}