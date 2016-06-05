package model;

import server.ClientInt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.board.Board;
import model.exceptions.ConfigurationErrorException;
import model.player.Player;

public class Game extends Thread {
	private List<ClientInt> players;
	private List<Player> listOfPlayers;
	private Board gameBoard;
	private TurnManager turnManager;
	private boolean isAddingPlayers;
	private int winningPlayer;
	private Config config;
	private boolean isComplete;
	private int maxNumberOfPlayers;

	public Game() throws ConfigurationErrorException {
		this.config = new Config();
		players = new ArrayList<ClientInt>();
		listOfPlayers = new ArrayList<>();
		isAddingPlayers = false;
		isComplete = false;
	}

	public synchronized void addPlayer(ClientInt client) {
		players.add(client);
		if (players.size() == maxNumberOfPlayers)
			isComplete = true;
	}

	public boolean isComplete() {
		return players.size() == 10;
	}

	public Board getBoard() {
		return gameBoard;
	}

	public TurnManager getTurnManager() {
		return this.turnManager;
	}

	public void run() {
		initializePlayers();
		boolean someoneWon = false;
		// This loops is for the regular game
		for (int i = 0; !someoneWon; i = (i + 1) % players.size()) {
			if (listOfPlayers.get(i).getSuspended())
				continue;
			turnManager = new TurnManager(listOfPlayers.get(i), gameBoard);
			if (listOfPlayers.get(i).getEmporium().size() == 0) {
				winningPlayer = i;
				someoneWon = true;
			}
		}
		// This loop is for the last round after that a player placed his 10th
		// emporium
		for (int j = (winningPlayer + 1) % players.size(); j != winningPlayer; j = (j + 1) % players.size()) {
			if (listOfPlayers.get(j).getSuspended())
				continue;
			turnManager = new TurnManager(listOfPlayers.get(j), gameBoard);
		}
		publishWinner();
	}

	public void initializePlayers() {
		for (int i = 0; i < players.size(); i++) {
			/*
			 * listOfPlayers.add(new Player(config.getMoney() + i,
			 * config.getHelpers() + i, config.getPolitic(), 10,
			 * config.getColors(), 0, 0, players.get(i)));
			 */
		}
	}

	public synchronized void logTimeIsUp() {
		this.isComplete = true;
	}

	public void publishWinner() {
		// TODO
	}

	public int getPlayersNumber() {
		return players.size();
	}
}
