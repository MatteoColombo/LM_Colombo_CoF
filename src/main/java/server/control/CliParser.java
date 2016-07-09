package server.control;

import org.apache.commons.cli.*;
/**
 * Parser for the string sent 
 * @author gianpaolobranca
 *
 */
public class CliParser {
	private Options opt;
	private CommandLineParser parser = new DefaultParser();
	
	public static final String OPTCOUNCIL ="council";
	public static final String OPTCITY ="city";
	public static final String OPTPERMISSION ="card";
	public static final String OPTREGION ="region";
	public static final String OPTCARDS ="politic";
	public static final String OPTCOLOR ="color";
	
	/**
	 * Create a new CliParser for the action parsing
	 */
	public CliParser() {

		opt = new Options()
			.addOption(OPTCOUNCIL, true, "")
			.addOption(OPTCOLOR, true, "")
			.addOption(OPTCITY, true, "")
			.addOption(OPTREGION, true, "")
			.addOption(OPTPERMISSION, true, "");	
		
		Option cards = new Option(OPTCARDS, true, "");
		cards.setArgs(4);
		cards.setOptionalArg(true);
		opt.addOption(cards);
	}
	
	/**
	 * parse the passed tokenized strings array representing an action
	 * @throws ParseException
	 */
	public CommandLine computeRequest(String[] args) throws ParseException {
		return parser.parse(opt, args);
	}
}
