package control;

import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import org.apache.commons.cli.*;

import model.action.*;
import model.board.Board;
import model.exceptions.IllegalActionException;
import model.player.Player;

public class Controller {
	private static final Options opt = new Options()
			.addOption("council", true, "")
			.addOption("council", true, "")
			.addOption("cards", true, "")
			.addOption("city", true, "")
			.addOption("king", false, "")
			.addOption("region", true, "")
			.addOption("permission", true, "");
	
	private Player player;
	private Board board;
	private CommandLineParser parser = new DefaultParser();
	private CommandLine parsedInput;
	private final Bindings actionWithName = new SimpleBindings();
	
	public Controller(Player player, Board board) {
		this.player = player;
		this.board = board;
		actionWithName.put("build emporium", ABuildEmporium.class);
		actionWithName.put("build emporium with king", ABuildEmporiumWithKing.class);
		actionWithName.put("buy permission card", ABuyPermissionCard.class);
		actionWithName.put("buy assistant", ABuyAssistant.class);
		actionWithName.put("extra action", AExtraMainAction.class);
		actionWithName.put("shuffle", AShufflePermissionCards.class);
		actionWithName.put("slide council", ASlideCouncil.class);
		actionWithName.put("slide council with assistant", ASlideCouncilWithAssistant.class);
	}
	
	public Action computeRequest(String request) throws IllegalActionException {
		String[] args = request.split(" ");
		if(!actionWithName.containsKey(args[0])) {
			throw new IllegalActionException("such action does not exist!");
		}
		
		Class<Action> selected = (Class<Action>) actionWithName.get(args[0]);

		try {
			parsedInput = parser.parse(opt, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return build(selected);
			// TODO understand the request
			/*
			 * possible requests:
			 * 
			 * 1) build emporium in <city name> with <permission card>
			 * 2) build emporium with king in <city name> with <cards..>
			 * 3) buy assistant
			 * 4) buy permission card <permission card> with <cards..>
			 * 5) buy extra main action
			 * 6) shuffle cards in <region>
			 * 7) slide council in <region/king> with <color> 
			 * 8) slide council as side action in <region/king> with <color> 
			 * 
			 * EXAMPLES:
			 * slide -council sea -cards orange,blue,multi
			 * buildEmporium -city Castrum -perm p2
			 * buildEmporiumKing -city Irori -cards blue,blue,pink
			 * 
			 */
	}
	
	private Action build(Class<Action> actionClass) {
		if(!parsedInput.getOptionValue("city").isEmpty()) {

		}
		return null;
	}
	
	/*private Action actionBuilder(Class<Action> Class, Object[] args) throws Exception{
		return (Action) Class.getConstructors()[0].newInstance(args);		
	}*/
}
