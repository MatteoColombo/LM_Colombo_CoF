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

import server.control.Controller;
import server.control.connection.ClientInt;
import server.control.connection.ServerInt;
import server.control.connection.rmi.RMIServer;
import server.control.connection.socket.SocketClientConnectionHandler;
import server.model.Game;
import server.model.GameListener;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;

/**
 * This is the main class on the server side. If you want to start a game
 * server, you have to execute this one.
 * 
 * @author Matteo Colombo
 *
 */
public class Server implements GameListener {

	private static List<Game> startingGames;
	private static List<Game> configuringGames;
	private static Map<Game, Controller> gameControllerMap;
	private static final String NAME = "ServerInt";
	private static Logger logger = Logger.getGlobal();
	private static ServerSocket socketServer;
	private static Configuration gamesConfig;
	private static boolean serverOn=true;
	
	/**
	 *  no object of the server is ever built so this method isn't needed
	 */
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
			try {
				gameControllerMap.get(newGame).configGame();
				gameControllerMap.get(newGame).addPlayer(client);
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
			startGame();
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
	public static synchronized void startGame() {
		Game g=startingGames.remove(0); 
		// set all the random parameters
		gameControllerMap.get(g).notifySendCityRewards();
		gameControllerMap.get(g).notifySetAllCouncil();
		gameControllerMap.get(g).notifySetAllPermissions();
		// start the game
		gameControllerMap.get(g).notifyGameStarted();
		g.start();
	}

	@Override
	public void gameEnded(Game game) {
		gameControllerMap.remove(game);
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
			ServerInt room = new RMIServer(gamesConfig);
			Registry registry = LocateRegistry.createRegistry(gamesConfig.getRmiPort());
			registry.rebind(NAME, room);
			logger.info("RMI server ready");
			socketServer = new ServerSocket(gamesConfig.getSocketPort());
			logger.info("Socket server ready");
			while (serverOn) {
				new SocketClientConnectionHandler(socketServer.accept(),gamesConfig.getTimeout()).start();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}