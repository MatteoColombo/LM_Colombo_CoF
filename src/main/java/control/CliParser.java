package control;

import org.apache.commons.cli.*;

public class CliParser {
	private Options opt;
	private CommandLineParser parser = new DefaultParser();
	
	public CliParser() {

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
	
	public CommandLine computeRequest(String[] args) throws ParseException {
		return parser.parse(opt, args);
	}
}
