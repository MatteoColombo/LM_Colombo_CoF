package client.cli.view;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.cli.model.CliBonus;
import client.cli.model.CliCity;
import client.cli.model.CliPermission;
import client.cli.model.CliPlayer;
import client.cli.model.CliRegion;
import client.cli.model.Game;
import client.control.ViewInterface;
import server.model.configuration.Configuration;

public class Cli implements ViewInterface {

	private static final String SEPARATOR = "---------------------------------------";
	private static final String[] EMPORIUM = {"🍅","🍇","🍈","🍉","🍐","🍒","🍓","🍍","🍄","🍔",""};
	private static final String PLAYER = "🏠";
	private PrintWriter writer;
	private Configuration config;
	private Game model;

	public Cli(Configuration config) {
		this.config = config;
		writer = new PrintWriter(System.out, true);
	}

	/**
	 * This sets the model so that the view can query when it's neede
	 * 
	 * @param model
	 *            the model
	 */
	public void setModel(Game model) {
		this.model = model;
	}

	/**
	 * This method is used to ask which type of connection the players wants to
	 * use
	 */
	public void showGetConnectionType() {
		writer.println("                             W E L C O M E !\n" + "\n" + "\n"
				+ "                    Select how do you want to connect:\n"
				+ "                          (1) Socket - (2) RMI");
	}

	/**
	 * this method is used to print the initial menu
	 */
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

	}

	/**
	 * This method prints the map, divided by regions It also prints the council
	 * and the permit cards
	 * 
	 * @param regions
	 *            the regions of the map
	 */
	public void printCities(List<CliRegion> regions) {
		for (CliRegion region : regions) {
			writer.println(SEPARATOR);
			writer.println("Region #" + (regions.indexOf(region) + 1));
			writer.print("Council: ");
			List<String> council = region.getCouncil();
			for (int i = council.size() - 1; i >= 0; i--)
				writer.print(council.get(i) + (i==(council.size()-2)?"\n":" "));	
			writer.println("Permission cards:");
			printPermission(new ArrayList<CliPermission>(Arrays.asList(region.getPermission())));
			for (CliCity city : region.getCities()) {
				writer.print("| ");
				printBonus(city);
				printEmporium(city);
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
	}

	/**
	 * Prints the players of the game and their statistics, For the local
	 * player, also the permit and the politic cards are printed
	 * 
	 * @param players
	 */
	public void printPlayers(List<CliPlayer> players) {
		writer.println("Players");
		for (int i = 0; i < players.size(); i++) {
			writer.println(SEPARATOR);
			writer.println(players.get(i).getName() + " " + (i == model.getMyIndex() ? PLAYER :EMPORIUM[i]));
			writer.println("Victory points: " + players.get(i).getVictory() + ", Coins: " + players.get(i).getCoins()
					+ ", Assitants: " + players.get(i).getAssistants() + ", Politics:"
					+ players.get(i).getPolitic().size() + ", Nobility: " + players.get(i).getNobility());
			if (i == model.getMyIndex()) {
				writer.print("Politic Cards: ");
				List<String> cards = players.get(i).getPolitic();
				cards.stream().forEach(card -> writer.print(card + " "));
				writer.println();
				printPermission(players.get(i).getPermission());
			}
		}
		writer.println(SEPARATOR);
	}

	private void printEmporium(CliCity city) {
		writer.print("(");
		for (int index : city.getEmporiums())
			writer.print(index == model.getMyIndex() ? PLAYER : EMPORIUM[index]);
		writer.print(")\t");
	}

	/**
	 * Prints the bonus of a city Prints the king if the city "contains" it
	 * 
	 * @param city
	 */
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

	/**
	 * Prints the permission cards, it is used for the map and for the players
	 * 
	 * @param permissions
	 */
	private void printPermission(List<CliPermission> permissions) {
		for (CliPermission perm : permissions) {
			writer.print((permissions.indexOf(perm) + 1) + ". ");
			perm.getCities().stream().map(city -> city.substring(0, 1).toUpperCase())
					.forEach(letter -> writer.print(letter.toUpperCase() + "\\"));
			perm.getReward().stream().forEach(reward -> writer
					.print(" " + reward.getName().substring(0, 3).toUpperCase() + " " + reward.getValue() + " "));
			writer.println(perm.isUsed() ? "| USED" : "| AVAILABLE");
		}
	}

	@Override
	public void printAskPlayersNumber(int max) {
		writer.println("Choose the maximum number of players. Game limit is " + max);
	}

	@Override
	public void printAskWhichMapToUse() {
		writer.println("Choose the number of the map:");
		List<String> maps = config.getMaps();
		for (int i = 1; i <= maps.size(); i++)
			writer.println(i + ". Map " + i);
	}

	@Override
	public void printAskWhatActionToDo() {
		writer.println("Type in the action that you want to do: (Read the README for instructions)");

	}

	@Override
	public void printAskPlayerName() {
		writer.println("Type in your name:");
	}

	@Override
	public void printIllegalAction(Exception e) {
		writer.println("Error: " + e.getMessage());

	}

	@Override
	public void showGame() {
		writer.println(SEPARATOR);
		writer.println("The game started");
		printCities(model.getRegions());
	}

	@Override
	public void showRoom() {
		writer.println("\nThe game will start soon");
	}

	@Override
	public void printMessage(String message) {
		writer.println(message);
	}

	@Override
	public void changeStatusToNobilityBonus(String message, String status) {
		writer.println(message);
	}

	@Override
	public void showMarket() {
		writer.println("The market started!");
	}

}
