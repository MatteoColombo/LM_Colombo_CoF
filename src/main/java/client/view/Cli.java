package client.view;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import client.model.GameProperty;
import client.model.ModelInterface;
import client.model.PlayerProperty;
import client.model.SimpleBonus;
import client.model.SimpleCity;
import client.model.SimpleRegion;
import client.model.cli.CliBonus;
import client.model.cli.CliCity;
import client.model.cli.CliPlayer;
import client.model.cli.CliRegion;
import client.model.cli.Game;
import javafx.beans.property.StringProperty;
import model.Configuration;
import model.board.Region;
import model.board.city.City;
import model.player.Player;
import model.player.PoliticCard;
import model.reward.Bonus;
import model.reward.Reward;

/**
 * 
 * @author gianpaolobranca
 *
 */
public class Cli implements ViewInterface {
	// NOTE: the following constants does not work in the eclipse console
	// but they will work in other terminals (i tested it in iTerm2 and OSX
	// Terminal)
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	private static final String BLOCK = "\u2588\u2588";
	private static final String SEPARATOR = "---------------------------------------";

	private PrintWriter writer;
	private Configuration config;
	private Game model;

	public Cli(Configuration config) {
		this.config = config;
		writer = new PrintWriter(System.out);
	}

	public void setModel(Game model) {
		this.model = model;
	}

	public void showGetConnectionType() {
		writer.print("                             W E L C O M E !\n" + "\n" + "\n"
				+ "                    Select how do you want to connect:\n"
				+ "                          (1) Socket - (2) RMI\n" + "                                 --> ");
		writer.flush();
	}

	public void showInitMenu() {
		writer.println("" + "                                  /   \\       \n"
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
		writer.println(ANSI_RED + BLOCK + ANSI_RESET + ANSI_YELLOW + BLOCK + ANSI_RESET + ANSI_BLUE + BLOCK + ANSI_RESET
				+ ANSI_WHITE + BLOCK + ANSI_RESET);
		writer.flush();
	}

	public void printCities(List<CliRegion> regions) {
		for (CliRegion region : regions) {
			writer.println(SEPARATOR);
			writer.println("Region #" + (regions.indexOf(region)+1));
			writer.print("Council: ");
			List<String> council= region.getCouncil();
			for(int i=council.size()-1; i>=0;i--)
				writer.print(council.get(i)+" ");
			writer.println();
			for (CliCity city : region.getCities()) {
				writer.print("| ");
				printBonus(city);
				if (city.getName().length() < 8)
					writer.print(city.getName() + "\t-->");
				else
					writer.print(city.getName() + "-->");
				for (String connected : city.getConnections()) {
					writer.print("\t" + connected);
				}
				writer.println();
			}
		}
		writer.println(SEPARATOR);
		writer.flush();
	}

	// if case of errors: add .toString() for each integer
	public void printPlayers(List<CliPlayer> players) {
		writer.println("Players");
		for (int i = 0; i < players.size(); i++) {
			writer.println(SEPARATOR);
			writer.println(players.get(i).getName());
			writer.println("Victory points: " + players.get(i).getVictory() + ", Coins: " + players.get(i).getCoins()
					+ ", Assitants: " + players.get(i).getAssistants() + ", Politics:"
					+ players.get(i).getPolitic().size() + ", Nobility: " + players.get(i).getNobility());
			if (i == model.getMyIndex()) {
				writer.print("Politic Cards: ");
				List<String> cards = players.get(i).getPolitic();
				cards.stream().forEach(card -> writer.print(card + " "));
				writer.println();
			}
		}
		writer.println(SEPARATOR);
		writer.flush();
	}

	private void printBonus(CliCity city) {
		writer.print("(");
		if (city.isHasKing())
			writer.print("♕ ");
		if (city.getBonus() != null)
			for (CliBonus b : city.getBonus())
				writer.print(b.getName().substring(0, 3).toUpperCase() + " " + b.getValue() + " ");
		writer.print(") ");
		for (int i = city.getBonus().size(); i < 3; i++)
			writer.print("\t");
	}

	@Override
	public void printAskPlayersNumber(int max) {
		writer.println("Choose the maximum number of players. Game limit is " + max);
		writer.flush();
	}

	@Override
	public void printAskWhichMapToUse() {
		writer.println("Choose the number of the map:");
		List<String> maps = config.getMaps();
		for (int i = 1; i <= maps.size(); i++) {
			writer.println(i + ". Map " + i);
		}
		writer.flush();
	}

	@Override
	public void printAskWhatActionToDo() {
		writer.println("Type in the action that you want to do: (Read the README for instructions)");
		writer.flush();
	}

	@Override
	public void printAskPlayerName() {
		writer.println("Type in your name:");
		writer.flush();
	}

	@Override
	public void printIllegalAction(Exception e) {
		writer.println("Error: " + e.getMessage());
		writer.flush();
	}

	@Override
	public void showGame() {
		writer.println(SEPARATOR);
		writer.println("The game started");
		writer.flush();
		printCities(model.getRegions());
	}

	@Override
	public void showRoom() {
		writer.println("\nThe game will start soon");
		writer.flush();
	}

	@Override
	public void printMessage(String message) {
		writer.println(message);
		writer.flush();
	}

}
