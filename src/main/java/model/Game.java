package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.board.Board;
import model.exceptions.ConfigurationErrorException;
import model.exceptions.XMLFileException;
import model.player.Player;
import view.ClientInt;

public class Game extends Thread {
	private List<Player> players;
	private Board gameBoard;
	private TurnManager turnManager;
	private int winningPlayer;
	private final Configuration config;
	private int maxNumberOfPlayers;
	private int choosenMap;

	public Game(Configuration gameConfig, ClientInt initialClient) throws ConfigurationErrorException {
		this.config = gameConfig;
		this.players = new ArrayList<>();
		this.maxNumberOfPlayers = config.getMaxNumberOfPlayer();
		try {
			initialClient.askMaxNumberOfPlayers(this.maxNumberOfPlayers);
			initialClient.askWichMapToUse(config.getMaps());
		} catch (IOException ioe) {
			initialClient.close();
			throw new ConfigurationErrorException(ioe);
		}
		try {
			this.gameBoard = new Board(gameConfig,choosenMap);
		} catch (XMLFileException e) {
			e.printStackTrace();
			throw new ConfigurationErrorException(e);
		}
	}

	public synchronized Player addPlayer(ClientInt client) {
		int playersInGame = players.size();
		Player p = new Player(10 + playersInGame, 1 + playersInGame, 6, 10, null, 0, 0, client);
		players.add(p);
		return p;
	}

	public boolean isComplete() {
		return players.size() == maxNumberOfPlayers;
	}

	public Board getBoard() {
		return gameBoard;
	}

	public TurnManager getTurnManager() {
		return this.turnManager;
	}

	public void run() { 
		boolean someoneWon = false;
		// This loops is for the regular game
		for (int i = 0; !someoneWon; i = (i + 1) % players.size()) {
			if (players.get(i).getSuspended())
				continue;
			turnManager = new TurnManager(players.get(i));
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
			turnManager = new TurnManager(players.get(j));
		}
		publishWinner();
	}

	public void publishWinner() {
		// TODO
	}

	public int getPlayersNumber() {
		return players.size();
	}

	public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		this.maxNumberOfPlayers = maxNumberOfPlayers;
	}

	public void setChoosenMap(int map) {
		this.choosenMap = map;
	}
}
