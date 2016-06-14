package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import model.Configuration;
import model.Game;
import model.TurnManager;
import model.exceptions.ConfigurationErrorException;
import model.exceptions.IllegalActionException;
import model.player.Player;
import server.Server;
import view.server.ClientInt;

public class Controller {
	private Game game;
	private CliParser parser;
	private ActionBuilder builder;
	private Map<ClientInt, Player> playersMap = new HashMap<>();
	private Logger logger = Logger.getGlobal();
	private Configuration config;
	
	public Controller(Game game, Configuration config) {
		this.game = game;
		this.config = config;
		this.parser = new CliParser();
	}

	/**
	 * Notifies all the players that a new one joined the game
	 * @param client 
	 */
	public void notifyPlayerJoined(ClientInt client){
		Set<ClientInt> clients = playersMap.keySet();
		for(ClientInt temp: clients){
			try {
				if(client != temp)
					temp.notifyPlayerJoined(playersMap.get(client));
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage(), e);
				playersMap.get(temp).setSuspension(true);
			}
		}
	}
	
	
	public void notifyGameStarted(){
		Set<ClientInt> clients= playersMap.keySet();
		for(ClientInt temp: clients){
			try {
				temp.notifyGameStarted();
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage(), e);
				playersMap.get(temp).setSuspension(true);
			}
		}
	}
	
	
	/**
	 * Tells the game to launch it's configuration method and then, when the
	 * configuration is completed, puts the game in a loading status where i can
	 * listen for other players
	 * 
	 * @throws ConfigurationErrorException
	 */
	public void configGame() throws ConfigurationErrorException {
		game.configGame();
		Server.acceptPlayers(game);
	}

	/**
	 * Parses the game configuration and then updates the model
	 * @param gameConfigMessage this is the string which containts the configuration
	 * @param client this is the client who is configuring; it is used in case of wrong config
	 * @throws IOException
	 */
	public void parseGameConfiguration(String gameConfigMessage, ClientInt client) throws IOException {
		String[] parameters = gameConfigMessage.split(" ");
		int map = 0;
		int players = config.getMaxNumberOfPlayer();
		try {
			players = Integer.parseInt(parameters[0]);
			if(players > config.getMaxNumberOfPlayer() || players < 0)
				throw new IllegalActionException("too many players");
			map = Integer.parseInt(parameters[1]);
			if(map > config.getMaps().size() || map < 0)
				throw new IllegalActionException("too many players");
		} catch (NumberFormatException | IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction();
			client.askConfiguration(config.getMaps(), config.getMaxNumberOfPlayer());
			return;
		}
		setMaxNumberOfPlayers(players);
		setChoosenMap(map);
	}


	/**
	 * Sets the max number of player that connect to a game
	 * 
	 * @param maxNumberOfPlayers
	 *            the max number of players
	 */
	private void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		game.setMaxNumberOfPlayers(maxNumberOfPlayers);
	}

	
	/**
	 * Sets the number of the choosen map in the game
	 * 
	 * @param choosenMap
	 *            the number of the choosen map in the maps' list
	 */
	private void setChoosenMap(int choosenMap) {
		game.setChoosenMap(choosenMap);
	}

	
	/**
	 * Notifies the game to generate a player and then adds it with it's client
	 * to the Player-ClientInt hashmap
	 * 
	 * @param client
	 *            the client who is used to generate a player
	 */
	public void addPlayer(ClientInt client) {
		playersMap.put(client, game.addPlayer(client));
		try {
			client.notifyGameLoading();
			client.sendPlayersList(game.getPlayers());
		} catch (IOException e) {
			logger.log(Level.INFO, e.getMessage(), e);
		}
	}

	
	/**
	 * translate a string action request from the client into its object
	 * representation It notify the client if something went wrong
	 * 
	 * @param s
	 *            the input from the cli
	 * @throws IOException
	 */
	public void performAction(ClientInt client, String s) throws IOException {
		this.builder = new ActionBuilder(game.getBoard(), config);
		Player player = playersMap.get(client);
		String[] args = s.split(" ");
		try {
			CommandLine cmd = parser.computeRequest(args);
			TurnManager tm = game.getTurnManager();

			switch (args[0]) {
			case "slide":
				tm.performAction(builder.makeASlideCouncil(player, cmd));
				return;
			case "assistant":
				tm.performAction(builder.makeABuyAssistant(player));
				return;
			case "extra":
				tm.performAction(builder.makeAExtraMainAction(player));
				return;
			case "secondarySlide":
				tm.performAction(builder.makeASlideCouncilWithAssistant(player, cmd));
				return;
			case "shuffle":
				tm.performAction(builder.makeAShufflePermissionCards(player, cmd));
				return;
			case "permission":
				tm.performAction(builder.makeAShufflePermissionCards(player, cmd));
				return;
			case "emporium":
				tm.performAction(builder.makeABuildEmporium(player, cmd));
				return;
			case "king":
				tm.performAction(builder.makeABuildEmporiumWithKing(player, cmd));
				return;
			case "end":
				tm.performAction(builder.makeAEndTurn(player, tm));
				return;
			default:
				throw new IllegalActionException("There is an error with the action");
			}
		} catch (IllegalActionException | ParseException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			client.notifyIllegalAction();
			client.askPlayerWhatActionToDo();
		}
	}
}
