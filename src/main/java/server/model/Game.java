package server.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.control.connection.ClientInt;
import server.control.dialogue.update.NotifyPlayerJoined;
import server.control.dialogue.update.UpdateEmporiumBuilt;
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
	private final Configuration config;
	private int maxNumberOfPlayers;
	private int choosenMap;
	private ClientInt initialClient;
	private Market market;
	private Logger logger = Logger.getGlobal();

	public Game(Configuration gameConfig, ClientInt initialClient) throws ConfigurationErrorException {
		this.config = gameConfig;
		this.players = new ArrayList<>();
		this.maxNumberOfPlayers = config.getMaxNumberOfPlayer();
		this.initialClient = initialClient;
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
			initialClient.askConfiguration(maxNumberOfPlayers);
		} catch (IOException ioe) {
			throw new ConfigurationErrorException(ioe);
		}
		try {
			this.gameBoard = new Board(config, choosenMap);
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
		
		while (!someoneWon) {
			someoneWon=regularCycle();
			runMarket(someoneWon);
		}
		
		lastCycle();
		calculateWinner();
		publishWinner();
	}

	private boolean regularCycle(){
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
	
	private void lastCycle(){
		for (int i = (winningPlayer + 1) % players.size(); countSuspendedPlayers() < (players.size() - 1)
				&& i != winningPlayer; i = (i + 1) % players.size()) {
			if (players.get(i).getSuspended())
				continue;
			turnManager.playTurn(i);
		}
	}
	private void runMarket(boolean someoneWon){
		if (!someoneWon && countSuspendedPlayers()< players.size()-1) {
			this.market = new Market(players);
			this.market.runMarket();
		}
	}
	
	private void checkAndConfigGameForTwo() {
		if(players.size()>2)
			return;
		Player server = new Player(config,null);
		server.setName("_Server_");
		sendServer(server);
		players.add(server);
		for (Region r : gameBoard.getRegions()) {
			PermissionCard card = new PermissionCard(r.getCities());
			for (City c : card.getCardCity()) {
				c.addEmporium(server.getEmporium().get(0));
				sendEmporium("_Server_", c.getName());
			}
		}

	}

	public void calculateWinner(){
		//TODO
	}
	public void publishWinner() {
		// TODO
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

	public Market getMarket() {
		return this.market;
	}

	private int countSuspendedPlayers() {
		return (int) players.stream().filter(Player::getSuspended).count();
	}

	private void sendEmporium(String name, String city) {
		for (Player p : players)
			if (!p.getSuspended()) {
				try {
					p.getClient().notify(new UpdateEmporiumBuilt(name, city));
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
	}

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
}
