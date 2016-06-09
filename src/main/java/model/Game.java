package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.board.Board;
import model.exceptions.ConfigurationErrorException;
import model.exceptions.XMLFileException;
import model.player.Player;
import view.server.ClientInt;

public class Game extends Thread {
	private List<Player> players;
	private Board gameBoard;
	private TurnManager turnManager;
	private int winningPlayer;
	private final Configuration config;
	private int maxNumberOfPlayers;
	private int choosenMap;
	private ClientInt initialClient;

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
			initialClient.askMaxNumberOfPlayers(this.maxNumberOfPlayers);
			initialClient.askWichMapToUse(config.getMaps());
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
	 * @param client the ClientInt of the player
	 * @return
	 */
	public synchronized Player addPlayer(ClientInt client) {
		Player p = new Player(config,getPlayersNumber(),client);
		players.add(p);
		return p;
	}

	/**
	 * It Checks if the game is complete
	 * @return true if the game is complete
	 */
	public boolean isComplete() {
		return players.size() == maxNumberOfPlayers;
	}

	/**
	 * Returns the game board
	 * @return the Board
	 */
	public Board getBoard() {
		return gameBoard;
	}

	/**
	 * Returns the turn manager
	 * @return
	 */
	public TurnManager getTurnManager() {
		return this.turnManager;
	}

	/**
	 * This is the method which is ran by the game thread.
	 * it manages the market and the game itself
	 */	
	@Override
	public void run() {
		boolean someoneWon = false;
		// This loops is for the regular game
		for (int i = 0; !someoneWon; i = (i + 1) % players.size()) {
			if (players.get(i).getSuspended())
				continue;
			turnManager = new TurnManager(players.get(i));
			turnManager.startTurn();
			if (players.get(i).getEmporium().isEmpty()) {
				winningPlayer = i;
				someoneWon = true;
			}
		}
		// This loop is for the last round after that a player placed his 10th
		// emporium
		for (int j = (winningPlayer + 1) % players.size(); j != winningPlayer; j = (j + 1) % players.size()) {
			if (players.get(j).getSuspended())
				continue;
			turnManager = new TurnManager(players.get(j));
			turnManager.startTurn();
		}
		publishWinner();
	}

	
	public void publishWinner() {
		// TODO
	}

	/**
	 * This returns the number of players connected
	 * @return an integer, the number of players
	 */
	public int getPlayersNumber() {
		return players.size();
	}

	/**
	 * This is used to change the max number of players, it is used before the game starts
	 * @param maxNumberOfPlayers
	 */
	public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		this.maxNumberOfPlayers = maxNumberOfPlayers;
	}

	/**
	 * This is used to choose the game map, it is used before the board is initialized
	 * @param map the index of the choosen map in the list
	 */
	public void setChoosenMap(int map) {
		this.choosenMap = map;
	}
}
