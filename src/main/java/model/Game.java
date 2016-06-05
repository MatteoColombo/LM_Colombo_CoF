package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.board.Board;
import model.exceptions.ConfigurationErrorException;
import model.player.Player;
import view.ClientInt;

public class Game extends Thread {
	private List<Player> players;
	private Board gameBoard;
	private TurnManager turnManager;
	private boolean isAddingPlayers;
	private int winningPlayer;
	private Config config;
	private boolean isComplete;
	private int maxNumberOfPlayers;

	public Game() throws ConfigurationErrorException {
		this.config = new Config();
		players = new ArrayList<>();
		isAddingPlayers = false;
		isComplete = false;
	}

	public synchronized Player addPlayer(ClientInt client) {
		int playersInGame= players.size();
		Player p= new Player(10+playersInGame, 1+playersInGame, 6, 10, null, 0, 0,client);
		players.add(p);
		return p;
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
			if (players.get(i).getSuspended())
				continue;
			turnManager = new TurnManager(players.get(i), gameBoard);
			if (players.get(i).getEmporium().size() == 0) {
				winningPlayer = i;
				someoneWon = true;
			}
		}
		// This loop is for the last round after that a player placed his 10th
		// emporium
		for (int j = (winningPlayer + 1) % players.size(); j != winningPlayer; j = (j + 1) % players.size()) {
			if (players.get(j).getSuspended())
				continue;
			turnManager = new TurnManager(players.get(j), gameBoard);
		}
		publishWinner();
	}

	public void initializePlayers() {
		for (int i = 0; i < players.size(); i++) {
			
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
