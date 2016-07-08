package client;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import client.cli.control.CliController;
import client.gui.control.MainApp;

public class WorkingClient {

	private static final String CLI_OPT = "cli";
	
	private static CommandLineParser parser = new DefaultParser();
	private static Options opt = new Options().addOption(CLI_OPT, false, "");
	private static final Logger log= Logger.getLogger( WorkingClient.class.getName() );

	
	private WorkingClient() {
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CommandLine cl;
		try {
			cl = parser.parse(opt, args);
			Thread t;
			if(cl.hasOption(CLI_OPT)) {
				CliController cliControl;
				cliControl = new CliController(sc);
				t = new Thread(cliControl);
			} else {
				MainApp def = new MainApp();
				t = new Thread(def);
				sc.close();
			}
			t.start();
		} catch (ParseException e) {
			log.log( Level.SEVERE, e.toString(), e );
		}
	}
}
