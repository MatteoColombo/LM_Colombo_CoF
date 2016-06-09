package control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
	private Logger logger= Logger.getGlobal();
	private Configuration config;
	
	public Controller(Game game, Configuration config) {
		this.game = game;
		this.config= config;
		this.parser = new CliParser();
	}

	/**
	 * Tells the game to launch it's configuration method and then, when the
	 * configuration is comleted, puts the game in a loading status where i can
	 * listen for other players
	 * 
	 * @throws ConfigurationErrorException
	 */
	public void configGame() throws ConfigurationErrorException {
		game.configGame(); 
		Server.acceptPlayers(game);
	}

	/**
	 * Sets the max number of player that connect to a game
	 * 
	 * @param maxNumberOfPlayers
	 *            the max number of players
	 */
	public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		game.setMaxNumberOfPlayers(maxNumberOfPlayers);
	}

	/**
	 * Sets the number of the choosen map in the game
	 * 
	 * @param choosenMap
	 *            the number of the choosen map in the maps' list
	 */
	public void setChoosenMap(int choosenMap) {
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
			default: 
				throw new IllegalActionException("There is an error with the action");
			}
		} catch (IllegalActionException | ParseException e) {
				logger.log(Level.SEVERE,e.getMessage(),e);
				client.notifyIllegalAction();
				client.askPlayerWhatActionToDo();
		}
	}
}
