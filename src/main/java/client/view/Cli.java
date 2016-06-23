package client.view;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.List;

import client.model.GameProperty;
import client.model.PlayerProperty;
import client.model.SimpleBonus;
import client.model.SimpleCity;
import client.model.SimpleRegion;
import javafx.beans.property.StringProperty;
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
	private GameProperty model;
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

	public Cli(GameProperty model) {
		this.model = model;
		writer = new PrintWriter(System.out);
	}

	@Override
	public void showGetConnectionType() {
		writer.print("                             W E L C O M E !\n" + "\n" + "\n"
				+ "                    Select how do you want to connect:\n"
				+ "                          (1) Socket - (2) RMI\n" + "                                 --> ");
		writer.flush();
	}

	// thanks to www.chris.com for awsome ascii art
	@Override
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

	@Override
	public void printCities() {
		List<SimpleRegion> regions = model.getMap().getRegions();

		for (SimpleRegion region : regions) {
			writer.println(SEPARATOR);
			for (SimpleCity city : region.getCities()) {
				writer.print("| ");
				printBonus(city);
				writer.print(city.getName() + " -->");
				for (String connected : city.getConnections()) {
					writer.print(" " + connected);
				}
				writer.println();
			}
		}
		writer.println(SEPARATOR);
		writer.flush();
	}

	// if case of errors: add .toString() for each integer
	public void printPlayers() {
		List<PlayerProperty> players = model.getPlayers();
		writer.println("Players");
		for (int i = 0; i < players.size(); i++) {
			writer.println(SEPARATOR);
			writer.println(players.get(i).getName());
			writer.println("Victory points: " + players.get(i).getVictory() + ", Coins: " + players.get(i).getCoins()
					+ ", Assitants: " + players.get(i).getAssistants() + ", Politics:"
					+ players.get(i).getPoliticCards().size() + ", Nobility: " + players.get(i).getNobility());
			if (i == model.getMyIndex()) {
				List<StringProperty> cards = players.get(i).getPoliticCards();
				for (StringProperty card : cards)
					writer.print(("multi".equals(card.getValue())? card.getValue()
							: model.getConfiguration().getColorsTranslationReverse().get(Color.decode(card.getValue())))+ " ");
				writer.println();
			}
		}
		writer.println(SEPARATOR);
		writer.flush();
	}

	private void printBonus(SimpleCity city) {
		writer.print("(");
		// TODO add if is capital
		if (city.hasKing().getValue())
			writer.print("♕");
		if (city.hasNoEmporium().getValue()) {
			for (SimpleBonus b : city.getBonuses()) {
				writer.print(b.getName().toUpperCase() + " " + b.getAmount());
			}
		} else {
			writer.print("???");
		}

		writer.print(") ");
	}

	@Override
	public void printAskPlayersNumber(int max) {
		writer.println("Choose the maximum number of players. Game limit is " + max);
		writer.flush();
	}

	@Override
	public void printAskWhichMapToUse() {
		writer.println("Choose the number of the map:");
		List<String> maps = model.getConfiguration().getMaps();
		for (int i = 0; i < maps.size(); i++) {
			writer.println((i + 1) + ". " + maps.get(i));
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
	public GameProperty getLocalModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showGame() {
		writer.println(SEPARATOR);
		writer.println("The game started");
		writer.flush();
		printCities();
		printPlayers();
	}

	@Override
	public void showRoom() {
		writer.println("\nThe game will start soon");
		writer.flush();
	}

	@Override
	public void playerJoined(Player p) {
		writer.println(p.getName() + " joined the game");
	}

	@Override
	public void setAllPlayers(List<Player> players) {
		writer.println("Players list:");
		for (int i = 0; i < players.size(); i++) {
			writer.println((i + 1) + ". " + players.get(i).getName());
		}
		writer.flush();
	}

	@Override
	public void updatePlayer(Player p, int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void isYourTurn() {
		writer.println("\nIt is your turn");
		writer.flush();
	}

	@Override
	public void setCityRewards(List<Reward> bonusList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void yourTurnEnded() {
		writer.println("Your turn ended");
		writer.println(SEPARATOR);
		writer.flush();
	}
}
