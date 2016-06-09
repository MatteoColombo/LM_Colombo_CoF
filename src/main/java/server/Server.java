 package server;

import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import control.Controller;
import model.Configuration;
import model.Game;
import model.exceptions.ConfigurationErrorException;
import view.server.ClientInt;
import view.server.SocketClientConnectionHandler;

public class Server {
	
	private static List<Game> startingGames;
	private static List<Game> configuringGames;
	private static Map<Game,Controller> gameControllerMap;
	private static final String NAME = "ServerInt";
	private static Logger logger = Logger.getGlobal();
	private static ServerSocket socketServer;
	private static Configuration gamesConfig;

	private Server(){}
	
	public static void login(ClientInt client){
		if(startingGames.isEmpty()){
			Game newGame;	
			try {
				newGame = new Game(gamesConfig, client);
			} catch (ConfigurationErrorException e) {
				logger.log(Level.SEVERE,e.getMessage(),e);
				client.close();
				return;
			}
			configuringGames.add(newGame);
			gameControllerMap.put(newGame, new Controller(newGame,gamesConfig));
			client.setController(gameControllerMap.get(newGame));
			gameControllerMap.get(newGame).addPlayer(client);
			try {
				gameControllerMap.get(newGame).configGame();
			} catch (ConfigurationErrorException e) {
				logger.log(Level.SEVERE,e.getMessage(),e);
				configuringGames.remove(newGame);
				gameControllerMap.remove(newGame);
				client.close();
			}
			
		}else{
			loginTogame(client);
		}
	}
	
	public static synchronized void loginTogame(ClientInt client){
		gameControllerMap.get(startingGames.get(0)).addPlayer(client);
		client.setController(gameControllerMap.get(startingGames.get(0)));
		if(startingGames.get(0).getPlayersNumber()==2 && !startingGames.get(0).isComplete())
			new InitializationTimeLimitManager(startingGames.get(0)).start();
		if(startingGames.get(0).isComplete())
			startingGames.remove(0).start();
	}
	public static synchronized void acceptPlayers(Game g){
		configuringGames.remove(g);
		startingGames.add(g);
	}
	
	public static synchronized void startGame(Game g){
		startingGames.remove(g);
		g.start();
	}

	public static void main(String[] args) {
		try {
			gamesConfig = new Configuration();
		} catch (ConfigurationErrorException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
			return;
		}
		gameControllerMap= new HashMap<>();
		startingGames= new ArrayList<>();
		configuringGames = new ArrayList<>();
		try {
			ServerInt room = new RMIServer();
			ServerInt stub = (ServerInt) UnicastRemoteObject.exportObject(room, 0);
			Registry registry = LocateRegistry.createRegistry(gamesConfig.getRmiPort());
			registry.rebind(NAME, stub);
			logger.info("RMI server ready");
			socketServer = new ServerSocket(gamesConfig.getSocketPort());
			logger.info("Socket server ready");
			while (true) {
				new SocketClientConnectionHandler(socketServer.accept()).start();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}
	}

}