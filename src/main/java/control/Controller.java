package control;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import model.Game;
import model.TurnManager;
import model.exceptions.IllegalActionException;
import model.player.Player;
import server.Server;
import view.ClientInt;

public class Controller {
	private Game game;
	private CliParser parser;
	private ActionBuilder builder;
	private Map<ClientInt, Player> playersMap = new HashMap<>();
	
	public Controller(Game game) {
		this.game = game;
		this.parser = new CliParser();
		this.builder = new ActionBuilder(game.getBoard());
	}
	
	public void configGame(ClientInt client){
		Server.acceptPlayers(game);
	}
	
	public void addPlayer(ClientInt client) {
		playersMap.put(client, game.addPlayer(client));
	}
	/**
	 * translate a string action request from the client into its object representation
	 * It notify the client if something went wrong
	 * @param s the input from the cli
	 */
	public void PerformAction(ClientInt client, String s) {
		Player player = playersMap.get(client);
		if(player == null) {
			//who is connecting is not in the current game
			// TODO client.tell("connection refused, cheater!");
			return;
		}
		String[] args = s.split(" ");
		try {
			CommandLine cmd = parser.computeRequest(args);
			TurnManager tm = game.getTurnManager();
			
			switch(args[0]) {
			case "slide": tm.performAction(builder.makeASlideCouncil(player, cmd));
				return;
			case "assistant": tm.performAction(builder.makeABuyAssistant(player));
				return;
			case "extra": tm.performAction(builder.makeAExtraMainAction(player));
				return;
			case "secondarySlide": tm.performAction(builder.makeASlideCouncilWithAssistant(player, cmd));
				return;
			case "shuffle": tm.performAction(builder.makeAShufflePermissionCards(player, cmd));
				return;
			case "permission": tm.performAction(builder.makeAShufflePermissionCards(player, cmd));
				return;
			case "emporium": tm.performAction(builder.makeABuildEmporium(player, cmd));
				return;
			case "king": tm.performAction(builder.makeABuildEmporiumWithKing(player, cmd));
				return;
			default: // TODO client.tell("wrong action")
				return;
			}
		} catch (IllegalActionException | ParseException e) {
			// TODO log a msg to the client
			e.printStackTrace();
		}
	}
}
