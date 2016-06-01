package control;

import org.apache.commons.cli.*;

import model.exceptions.IllegalActionException;

public class CliParser {
	private Options opt;
	private CommandLineParser parser = new DefaultParser();
	//private final Bindings actionWithName = new SimpleBindings();
	
	public CliParser() {
		/*actionWithName.put("build emporium", ABuildEmporium.class);
		actionWithName.put("build emporium with king", ABuildEmporiumWithKing.class);
		actionWithName.put("buy permission card", ABuyPermissionCard.class);
		actionWithName.put("buy assistant", ABuyAssistant.class);
		actionWithName.put("extra action", AExtraMainAction.class);
		actionWithName.put("shuffle", AShufflePermissionCards.class);
		actionWithName.put("slide council", ASlideCouncil.class);
		actionWithName.put("slide council with assistant", ASlideCouncilWithAssistant.class);*/
		opt = new Options()
			.addOption("council", true, "")
			.addOption("color", true, "")
			.addOption("city", true, "")
			.addOption("region", true, "")
			.addOption("permission", true, "");
		
		
		Option cards = new Option("cards", true, "");
		// should be 4 but whatever...
		cards.setArgs(10);
		opt.addOption(cards);
	}
	
	public CommandLine computeRequest(String request) throws IllegalActionException, ParseException {
		String[] args = request.split(" ");
		return parser.parse(opt, args);
	}
			// TODO understand the request
			/*
			 * EXAMPLES:
			 * slide -council sea -cards orange,blue,multi
			 * buildEmporium -city Castrum -perm p2
			 * buildEmporiumKing -city Irori -cards blue,blue,pink
			 * 
			 */
}
