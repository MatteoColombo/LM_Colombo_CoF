package server.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import server.model.board.map.MapLoader;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.XMLFileException;
import server.model.market.Market;
import server.model.player.Emporium;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.VictoryPoints;

/**
 * A class that represents the Game itself; for each one on the Server, a new
 * Game class is instantiated.
 * <p>
 * The {@link #configGame() Configurations of the current Game} are decided by
 * the first Client that will start a new instance of it: in the specific he can
 * or {@link #setMaxNumberOfPlayers(int) set the max number} of
 * {@link #getPlayers() Players} and {@link #setChoosenMap(int) the Map to play}
 * or letting the Game itself to {@link #setConfigurationType(boolean) generate
 * all randomly}; in any case {@link #getPlayersNumber() new Players} can
 * {@link #addPlayer(ClientInt) be added} checking that {@link #isComplete() the
 * set max will not be exceeded}. Then the Game will initialize some of its main
 * objects such as {@link #getBoard() the Board}, {@link #getMarket() the
 * Market} and {@link #getTurnManager() the TurnManager}; it will also create
 * {@link #addListener(GameListener) a GameListener} as an event filter for the
 * other classes.
 * 
 * @author Matteo Colombo
 * @see Board
 * @see ClientInt
 * @see Configuration
 * @see GameListener
 * @see MapLoader
 * @see Market
 * @see Player
 * @see TurnManager
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
	 * Instantiates the {@link ClientInt initial Client} and saves the
	 * {@link Configuration Configurations}.
	 * 
	 * @param gameConfig
	 *            the default Configurations
	 * @param initialClient
	 *            the initial Client that will configure the Game
	 * @throws ConfigurationErrorException
	 * @see Game
	 */
	public Game(Configuration gameConfig, ClientInt initialClient) throws ConfigurationErrorException {
		this.config = gameConfig;
		this.players = new ArrayList<>();
		this.maxNumberOfPlayers = config.getMaxNumberOfPlayer();
		this.initialClient = initialClient;
		listeners = new ArrayList<>();
	}

	/**
	 * Configures the current Game, asking to the {@link ClientInt initial
	 * Client} if randomly generate all the Game elements or manually set the
	 * max number of {@link Player Player} and the Map to play.
	 * 
	 * @throws ConfigurationErrorException
	 * @see Game
	 * @see Random
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
	 * Generates and initializes a {@link ClientInt Client} of a new
	 * {@link Player} adding it to the Players list and then returning itself.
	 * 
	 * @param client
	 *            the Client of the new Player
	 * @return the new Player
	 * @see Game
	 */
	public synchronized Player addPlayer(ClientInt client) {
		Player p = new Player(config, getPlayersNumber(), client, gameBoard.getNobleTrack());
		players.add(p);
		return p;
	}

	/**
	 * Checks if the max availability of {@link Player Players} has been reached
	 * or not.
	 * 
	 * @return <code>true</code> if the Game is full; <code>false</code>
	 *         otherwise
	 * @see Game
	 */
	public boolean isComplete() {
		return players.size() == maxNumberOfPlayers;
	}

	/**
	 * Returns the {@link Board}.
	 * 
	 * @return the Board
	 * @see Game
	 */
	public Board getBoard() {
		return gameBoard;
	}

	/**
	 * Returns the {@link TurnManager}.
	 * 
	 * @return the TurnManager
	 * @see Game
	 */
	public TurnManager getTurnManager() {
		return this.turnManager;
	}

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
	 * Removed the Server before giving extra {@link VictoryPoints} so that it
	 * is not sent to the {@link ClientInt Client} with the classification; this
	 * only if the Server was playing.
	 * 
	 * @see Game
	 */
	private void removeServer() {
		Player temp = players.get(players.size() - 1);
		if (SERVERNAME.equals(temp.getName()) && temp.getVictoryPoints().getAmount() == -1)
			players.remove(temp);
	}

	/**
	 * Removes all the references and notifies from the Game.
	 * 
	 * @see Game
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
	 * Executes the regular {@link Game} turn cycle; this until a {@link Player}
	 * has won.
	 * 
	 * @return <code>true</code> if a {@link Player} have placed its tenth
	 *         Emporium; <code>false</code> otherwise
	 * @see Game
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
	 * Allows all the {@link Player Players} but the one that placed the tenth
	 * {@link Emporium} to play another turn.
	 * 
	 * @see Game
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
	 * Manages the {@link Market}.
	 * 
	 * @param someoneWon
	 *            code>true</code> if a {@link Player} have placed its tenth
	 *            Emporium; <code>false</code> otherwise
	 * @see Game
	 */
	private void runMarket(boolean someoneWon) {
		if (!someoneWon && countSuspendedPlayers() < players.size() - 1) {
			this.market = new Market(players);
			this.market.runMarket();
		}
	}

	/**
	 * Configures the Game with the 2 {@link Player Players} regulation in case
	 * there are only 2 {@link ClientInt Clients} connected.
	 * 
	 * @see Game
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
	 * Assigns the extra {@link VictoryPoints} to the {@link Player} who places
	 * the tenth {@link Emporium} and to the one who has more
	 * {@link PermissionCard PermissionCards}.
	 * 
	 * @see Game
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
	 * Sorts the {@link Player Players} list according to their
	 * {@link VictoryPoints}.
	 * 
	 * @see Game
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
	 * Publishes the final {@link Player Players} classification.
	 * 
	 * @see Game
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
	 * Returns the number of connected {@link Player Players}.
	 * 
	 * @return the number of connected Players
	 * @see Game
	 */
	public int getPlayersNumber() {
		return players.size();
	}

	/**
	 * Sets the max number of {@link Player Players} before the Game starts.
	 * 
	 * @param maxNumberOfPlayers
	 *            the max number of Players
	 * @see Game
	 */
	public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		this.maxNumberOfPlayers = maxNumberOfPlayers;
	}

	/**
	 * Sets the desired Game Map before the {@link Board} is initialized.
	 * 
	 * @param map
	 *            the index of the chosen Map
	 * @see Game
	 */
	public void setChoosenMap(int map) {
		this.choosenMap = map;
	}

	/**
	 * Return the list of the all the {@link Player Players}.
	 * 
	 * @return all the Players
	 * @see Game
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Returns the {@link Market}.
	 * 
	 * @return the Market
	 * @see Game
	 */
	public Market getMarket() {
		return this.market;
	}

	/**
	 * Counts the number of suspended {@link Player Players}.
	 * 
	 * @return the number of suspended Players
	 * @see Game
	 */
	private int countSuspendedPlayers() {
		return (int) players.stream().filter(Player::getSuspended).count();
	}

	/**
	 * Sends the {@link Emporium} of the Server to the {@link ClientInt Clients}
	 * .
	 * 
	 * @param name
	 *            the Server name
	 * @param city
	 *            the City where the Emporium is built
	 * @see Game
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
	 * Sends the Server fake {@link Player} to the {@link ClientInt Clients}.
	 * 
	 * @param server
	 *            the fake Player
	 * @see Game
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
	 * Adds a {@link GameListener} to the Game own list.
	 * 
	 * @param listener
	 *            a GameListener
	 * @see Game
	 */
	public void addListener(GameListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Sets the Configuration method as manually or random.
	 * 
	 * @param randomConfig
	 *            <code>true</code> if is random; false otherwise
	 * @see Game
	 */
	public void setConfigurationType(boolean randomConfig) {
		this.randomConfig = randomConfig;
	}
}
