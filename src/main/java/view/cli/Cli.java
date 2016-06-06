package view.cli;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import model.board.Board;
import model.board.ColorConstants;
import model.board.Region;
import model.board.city.City;
import model.exceptions.XMLFileException;
import model.player.Player;

/**
 * 
 * @author gianpaolobranca
 *
 */
public class Cli {
	// NOTE: the following constants does not work in the eclipse console
	// but they will work in other terminals (i tested it in iTerm2 and OSX Terminal)
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	private PrintWriter writer;
	private Scanner scanner;
	public Cli() {
		writer = new PrintWriter(System.out);
		scanner = new Scanner(System.in);
	}

	public String getConnectionType() {
		writer.print("                             W E L C O M E !\n"
				+ "\n"
				+ "\n"
				+ "                    Select how do you want to connect:\n"
				+ "                          (1) Socket - (2) RMI\n"
				+ "                                 --> ");
		writer.flush();
		return scanner.nextLine();
	}
	
	// thanks to www.chris.com for awsome ascii art
	public void showCoolInitMenu() {
		writer.println(""
				+ "                                  /   \\       \n"
				+ " _                        )      ((   ))     (\n"
				+ "(@)                      /|\\      ))_((     /|\\\n" 
				+ "|-|\\                    / | \\    (/\\|/\\)   / | \\                      (@)\n"
				+ "| | -------------------/--|-voV---\\`|'/--Vov-|--\\---------------------|-|\n"
				+ "|-|                         '^`   (o o)  '^`                          | |\n"
				+ "| |                               `\\Y/'                               |-|\n"
				+ "|-|                                                                   | |\n"
				+ "| |                   C O U N C I L   O F   F O U R                   |-|\n"
				+ "|-|                                                                   | |\n"
				+ "| |                       Copyright 2016 LM_20                        |-|\n" 
				+ "|_|___________________________________________________________________| |\n"
				+ "(@)              l   /\\ /         ( (       \\ /\\   l                `\\|-|\n"
				+ "                 l /   V           \\ \\       V   \\ l                  (@)\n"
				+ "                 l/                _) )_          \\I\n"
				+ "                                   `\\ /'\n");

		writer.flush();
	}
	// this is just a trial for print colored blocks
	public void printCouncil() {
		writer.println( ANSI_RED + "\u2588\u2588" + ANSI_RESET + ANSI_YELLOW + "\u2588\u2588" + ANSI_RESET + ANSI_BLUE + "\u2588\u2588" + ANSI_RESET + ANSI_WHITE + "\u2588\u2588" + ANSI_RESET);
		writer.flush();
	}
	
	public void printCities(List<Region> regions) {
		
		for(Region region: regions) {
			writer.println("---------------------------------------");
			for(City city: region.getCities()) {
				writer.print("| " + city.getName() + " -->");
				for(City connected: city.getConnectedCities()) {
					writer.print(" " + connected.getName());
				}
				writer.println();
			}
		}
		writer.println("---------------------------------------");
		writer.flush();
	}
	// if case of errors: add .toString() for each integer
	public void printPlayers(List<Player> players) {
		int index = 1;
		for(Player player: players) {
			writer.println("---------------------------------------\n"
						 + "| Player " + index + "\n"
						 + "| Victory Points: " + player.getVictoryPoints().getAmount() + " Coins: " + player.getCoins().getAmount() + " Assistants: " + player.getAssistants().getAmount() + "\n"
						 + "| Cards in hand: " + player.getPoliticCard().size() + "Nobility: " + player.getNoblePoints().getAmount());
		}
		writer.println("---------------------------------------");
	}
	
	public static void main(String[] args) {
		Cli cli = new Cli();
		cli.showCoolInitMenu();
		try {
			Board board = new Board("src/main/resources/map.xml", "src/main/resources/nobtrack.xml", 6, 4, ColorConstants.getCardsColors());
			cli.printCities(board.getRegions());
		} catch (XMLFileException e) {
			e.printStackTrace();
		}
	}
}
