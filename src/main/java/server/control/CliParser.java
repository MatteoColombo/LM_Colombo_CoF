package server.control;

import org.apache.commons.cli.*;

public class CliParser {
	private Options opt;
	private CommandLineParser parser = new DefaultParser();
	
	public static final String OPTCOUNCIL ="council";
	public static final String OPTCITY ="city";
	public static final String OPTPERMISSION ="permission";
	public static final String OPTREGION ="region";
	public static final String OPTCARDS ="cards";
	public static final String OPTCOLOR ="color";
	
	public CliParser() {

		opt = new Options()
			.addOption(OPTCOUNCIL, true, "")
			.addOption(OPTCOLOR, true, "")
			.addOption(OPTCITY, true, "")
			.addOption(OPTREGION, true, "")
			.addOption(OPTPERMISSION, true, "");	
		
		Option cards = new Option("cards", true, "");
		cards.setArgs(4);
		cards.setOptionalArg(true);
		opt.addOption(cards);
	}
	
	public CommandLine computeRequest(String[] args) throws ParseException {
		return parser.parse(opt, args);
	}
}
