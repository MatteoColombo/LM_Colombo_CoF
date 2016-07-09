package server.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import server.control.connection.ClientInt;
import server.control.instruction.notify.NotifyClassification;
import server.control.instruction.update.NotifyPlayerJoined;
import server.control.instruction.update.UpdateEmporiumBuilt;
import server.model.board.Board;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.XMLFileException;
import server.model.market.Market;
import server.model.player.PermissionCard;
import server.model.player.Player;

/**
 * This class represents the game class, for every game on the server a new Game
 * class is instantiated
 * 
 * @author Matteo Colombo
 *
 */
public class Game extends Thread {
	private List<Player> players;
	private Board gameBoard;
	private TurnManager turnManager;
	private int winningPlayer;
	private Configuration config;
	private int maxNumberOfPlayers;
	private int choosenMap;
	private ClientInt initialClient;
	private Market market;
	private Logger logger = Logger.getGlobal();
	private static final String SERVERNAME = "_Server_";
	private List<GameListener> listeners;
	private boolean randomConfig;

	/**
	 * Instantiates some objects and saves the configuration
	 * 
	 * @param gameConfig
	 *            the configuration object
	 * @param initialClient
	 *            the client which is configuring the game
	 * @throws ConfigurationErrorException
	 */
	public Game(Configuration gameConfig, ClientInt initialClient) throws ConfigurationErrorException {
		this.config = gameConfig;
		this.players = new ArrayList<>();
		this.maxNumberOfPlayers = config.getMaxNumberOfPlayer();
		this.initialClient = initialClient;
		listeners = new ArrayList<>();
	}

	/**
	 * This is the game configuration class, it asks to the initial client for
	 * the max number of players and for the choosen gamemap, then it generates
	 * the game elements
	 * 
	 * @throws ConfigurationErrorException
	 */
	public void configGame() throws ConfigurationErrorException {
		try {
			initialClient.askConfigurationMethod();
			if (!randomConfig)
				initialClient.askConfiguration(maxNumberOfPlayers);
		} catch (IOException ioe) {
			throw new ConfigurationErrorException(ioe);
		}
		try {
			gameBoard = new Board(config, choosenMap, randomConfig);
		} catch (XMLFileException e) {
			throw new ConfigurationErrorException(e);
		}
	}

	/**
	 * This generates and initializes a player. The player is added to the
	 * players list and then returned.
	 * 
	 * @param client
	 *            the ClientInt of the player
	 * @return
	 */
	public synchronized Player addPlayer(ClientInt client) {
		Player p = new Player(config, getPlayersNumber(), client, gameBoard.getNobleTrack());
		players.add(p);
		return p;
	}

	/**
	 * It Checks if the game is complete
	 * 
	 * @return true if the game is complete
	 */
	public boolean isComplete() {
		return players.size() == maxNumberOfPlayers;
	}

	/**
	 * Returns the game board
	 * 
	 * @return the Board
	 */
	public Board getBoard() {
		return gameBoard;
	}

	/**
	 * Returns the turn manager
	 * 
	 * @return
	 */
	public TurnManager getTurnManager() {
		return this.turnManager;
	}

	/**
	 * This is the method which is ran by the game thread. it manages the market
	 * and the game itself
	 */
	@Override
	public void run() {
		boolean someoneWon = false;
		checkAndConfigGameForTwo();
		turnManager = new TurnManager(players, config.getColorsList());
		while (!someoneWon && countSuspendedPlayers() < players.size() - 1) {
			someoneWon = regularCycle();
			runMarket(someoneWon);
		}

		lastCycle();
		removeServer();
		giveExtraPoints();
		calculateWinner();
		publishWinner();
		cleanUp();
	}

	/**
	 * If the server was playing, it is removed before giving extra points so
	 * that it isnt' sent to the client with the classification
	 */
	private void removeServer() {
		Player temp = players.get(players.size() - 1);
		if (SERVERNAME.equals(temp.getName()) && temp.getVictoryPoints().getAmount() == -1)
			players.remove(temp);
	}

	/**
	 * Removes the references and notifies the listeners
	 */
	private void cleanUp() {
		for (Player p : players)
			p.removeClient();
		this.gameBoard = null;
		this.turnManager = null;
		this.players.clear();
		this.players = null;
		this.config = null;
		this.market = null;
		this.logger = null;
		for (GameListener listener : listeners)
			listener.gameEnded(this);
		listeners.clear();
		listeners = null;
	}

	/**
	 * This is the method which executes the regulare game cycle
	 * 
	 * @return true if a player placed the tenth emporium
	 */
	private boolean regularCycle() {
		for (int i = 0; countSuspendedPlayers() < (players.size() - 1) && i < players.size(); i++) {
			if (players.get(i).getSuspended())
				continue;
			turnManager.playTurn(i);
			if (players.get(i).getEmporium().isEmpty()) {
				winningPlayer = i;
				return true;
			}
		}
		return false;
	}

	/**
	 * This is the extra cycle which allows all the players but the one which
	 * placed the tenth emporium to play another turn
	 */
	private void lastCycle() {
		for (int i = (winningPlayer + 1) % players.size(); countSuspendedPlayers() < (players.size() - 1)
				&& i != winningPlayer; i = (i + 1) % players.size()) {
			if (players.get(i).getSuspended())
				continue;
			turnManager.playTurn(i);
		}
	}

	/**
	 * This is the method which manages the market
	 * 
	 * @param someoneWon
	 */
	private void runMarket(boolean someoneWon) {
		if (!someoneWon && countSuspendedPlayers() < players.size() - 1) {
			this.market = new Market(players);
			this.market.runMarket();
		}
	}

	/**
	 * This is the method which configures the game with the 2 players
	 * regulation, in case there are only 2 clients connected
	 */
	private void checkAndConfigGameForTwo() {
		if (players.size() > 2)
			return;
		Player server = new Player(config, null);
		server.setName(SERVERNAME);
		sendServer(server);
		players.add(server);
		for (Region r : gameBoard.getRegions()) {
			PermissionCard card = new PermissionCard(r.getCities());
			for (City c : card.getCardCity()) {
				c.addEmporium(server.getEmporium().get(0));
				sendEmporium(players.indexOf(server), c.getName());
			}
		}

	}

	/**
	 * Assigns the extra points to the player who places the tenth emporium and
	 * to the one who has more permission cards,
	 */
	private void giveExtraPoints() {
		players.get(winningPlayer).getVictoryPoints().increaseAmount(3);

		int maxNobility = players.stream().mapToInt(p -> p.getNobility().getAmount()).max().orElse(-1);
		int playersWithMax = (int) players.stream().filter(p -> p.getNobility().getAmount() == maxNobility).count();
		players.stream().filter(p -> p.getNobility().getAmount() == maxNobility)
				.forEach(p -> p.getVictoryPoints().increaseAmount(5));
		if (playersWithMax > 1) {
			int secondNobility = players.stream().filter(p -> p.getNobility().getAmount() == maxNobility)
					.mapToInt(p -> p.getNobility().getAmount()).max().orElse(-1);
			players.stream().filter(p -> p.getNobility().getAmount() == secondNobility)
					.forEach(p -> p.getVictoryPoints().increaseAmount(2));
		}

		int maxPermitCards = players.stream().mapToInt(p -> p.getPermissionCard().size()).max().orElse(-1);
		players.stream().filter(p -> p.getPermissionCard().size() == maxPermitCards)
				.forEach(p -> p.getVictoryPoints().increaseAmount(3));

	}

	/**
	 * It sorts the players list based on victory points
	 */
	private void calculateWinner() {
		players = players.stream().sorted((p1, p2) -> {
			if (p1.getVictoryPoints().getAmount() > p2.getVictoryPoints().getAmount())
				return -1;
			else if (p1.getVictoryPoints().getAmount() < p2.getVictoryPoints().getAmount())
				return 1;
			else {
				if ((p1.getPoliticCard().size() + p1.getAssistants().getAmount()) > (p2.getPoliticCard().size()
						+ p2.getAssistants().getAmount()))
					return 1;
				else if ((p1.getPoliticCard().size() + p1.getAssistants().getAmount()) < (p2.getPoliticCard().size()
						+ p2.getAssistants().getAmount()))
					return 1;
				else
					return 0;
			}
		}).collect(Collectors.toList());
	}

	/**
	 * publishes the classification
	 */
	private void publishWinner() {
		List<Player> clones = new ArrayList<>();
		for (Player p : players) {
			clones.add(p.getClientCopy());
		}
		for (Player p : players) {
			try {
				if (p.getSuspended())
					continue;
				p.getClient().notify(new NotifyClassification(clones));
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	/**
	 * This returns the number of players connected
	 * 
	 * @return an integer, the number of players
	 */
	public int getPlayersNumber() {
		return players.size();
	}

	/**
	 * This is used to change the max number of players, it is used before the
	 * game starts
	 * 
	 * @param maxNumberOfPlayers
	 */
	public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		this.maxNumberOfPlayers = maxNumberOfPlayers;
	}

	/**
	 * This is used to choose the game map, it is used before the board is
	 * initialized
	 * 
	 * @param map
	 *            the index of the choosen map in the list
	 */
	public void setChoosenMap(int map) {
		this.choosenMap = map;
	}

	/**
	 * Return the list of the players
	 * 
	 * @return
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the market
	 */
	public Market getMarket() {
		return this.market;
	}

	/**
	 * Counts the number of suspended players
	 * 
	 * @return the number of suspended players
	 */
	private int countSuspendedPlayers() {
		return (int) players.stream().filter(Player::getSuspended).count();
	}

	/**
	 * Sends the emporium of the Server to the clients
	 * 
	 * @param name
	 *            the server name
	 * @param city
	 *            the city in which the emporium is built
	 */
	private void sendEmporium(int index, String city) {
		for (Player p : players)
			if (!p.getSuspended()) {
				try {
					p.getClient().notify(new UpdateEmporiumBuilt(index, city));
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
	}

	/**
	 * Sends the server fake player to the clients
	 * 
	 * @param server
	 *            the fake player
	 */
	private void sendServer(Player server) {
		for (Player p : players)
			if (!p.getSuspended()) {
				try {
					p.getClient().notify(new NotifyPlayerJoined(server.getClientCopy()));
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}

			}
	}

	/**
	 * Adds a GameListener to the list
	 * 
	 * @param listener
	 */
	public void addListener(GameListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Sets the configuration method. True if random, false otherwise
	 * 
	 * @param randomConfig
	 */
	public void setConfigurationType(boolean randomConfig) {
		this.randomConfig = randomConfig;
	}
}
