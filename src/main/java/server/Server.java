package server;

import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

/**
 * This is the main class on the server side. If you want to start a game
 * server, you have to execute this one.
 * 
 * @author Matteo Colombo
 *
 */
public class Server {

	private static List<Game> startingGames;
	private static List<Game> configuringGames;
	private static Map<Game, Controller> gameControllerMap;
	private static final String NAME = "ServerInt";
	private static Logger logger = Logger.getGlobal();
	private static ServerSocket socketServer;
	private static Configuration gamesConfig;

	private Server() {
	}

	/**
	 * This is the method that logs a client in the game. If there's already a
	 * starting game, the player is added to it. If he is the second one, it
	 * starts the countdown and after 20 seconds the game will start anyhow. If
	 * there isn't any starting game, a new one is created as "configuring"
	 * game. Also a controller is created and the player added so that the
	 * configuration procedure can start.
	 * 
	 * @param client
	 *            the client who wants to join a game
	 */
	public static void login(ClientInt client) {
		if (startingGames.isEmpty()) {
			Game newGame;
			try {
				newGame = new Game(gamesConfig, client);
			} catch (ConfigurationErrorException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				client.close();
				return;
			}
			configuringGames.add(newGame);
			gameControllerMap.put(newGame, new Controller(newGame, gamesConfig));
			client.setController(gameControllerMap.get(newGame));
			gameControllerMap.get(newGame).addPlayer(client);
			try {
				gameControllerMap.get(newGame).configGame();
				gameControllerMap.get(newGame).notifyGameLoading(client);
			} catch (ConfigurationErrorException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				configuringGames.remove(newGame);
				gameControllerMap.remove(newGame);
				client.close();
			}

		} else {
			loginTogame(client);
		}
	}

	/**
	 * This is the support method which actually adds players to a game which is
	 * loading and accepting players
	 * 
	 * @param client
	 */
	private static synchronized void loginTogame(ClientInt client) {
		gameControllerMap.get(startingGames.get(0)).addPlayer(client);
		gameControllerMap.get(startingGames.get(0)).notifyGameLoading(client);
		gameControllerMap.get(startingGames.get(0)).notifyPlayerJoined(client);
		client.setController(gameControllerMap.get(startingGames.get(0)));
		if (startingGames.get(0).getPlayersNumber() == 2 && !startingGames.get(0).isComplete())
			new InitializationTimeLimitManager(startingGames.get(0)).start();
		if (startingGames.get(0).isComplete())
			startingGames.remove(0).start();
	}

	/**
	 * This is the method which sets a game in the "accepting players" status,
	 * from now until when the game is full, every new client will be added to
	 * it
	 * 
	 * @param g
	 */
	public static synchronized void acceptPlayers(Game g) {
		configuringGames.remove(g);
		startingGames.add(g);
	}

	/**
	 * This is the method which starts a game when it is full or when the
	 * countdown is up
	 * 
	 * @param g
	 */
	public static synchronized void startGame(Game g) {
		startingGames.remove(g);
		g.start();
	}

	/**
	 * This is the main method of the server. It turns on both the RMI and
	 * Socket servers
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			gamesConfig = new Configuration();
		} catch (ConfigurationErrorException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		gameControllerMap = new HashMap<>();
		startingGames = new ArrayList<>();
		configuringGames = new ArrayList<>();
		try {
			ServerInt room = new RMIServer();
			Registry registry = LocateRegistry.createRegistry(gamesConfig.getRmiPort());
			registry.rebind(NAME, room);
			logger.info("RMI server ready");
			socketServer = new ServerSocket(gamesConfig.getSocketPort());
			logger.info("Socket server ready");
			while (true) {
				new SocketClientConnectionHandler(socketServer.accept()).start();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}