package client.view;

import java.io.PrintWriter;
import java.util.List;

import client.model.GameProperty;
import model.board.Region;
import model.board.city.City;
import model.player.Player;
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
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String BLOCK = "\u2588\u2588";
	public static final String SEPARATOR = "---------------------------------------";
	
	private PrintWriter writer;

	public Cli() {
		writer= new PrintWriter(System.out);
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
		writer.println(ANSI_RED + BLOCK + ANSI_RESET + ANSI_YELLOW + BLOCK + ANSI_RESET + ANSI_BLUE
				+ BLOCK + ANSI_RESET + ANSI_WHITE + BLOCK + ANSI_RESET);
		writer.flush();
	}
	@Override
	public void printCities(List<Region> regions) {

		for (Region region : regions) {
			writer.println(SEPARATOR);
			for (City city : region.getCities()) {
				writer.print("| ");
				printBonus(city);
				writer.print(city.getName() + " -->");
				for (City connected : city.getConnectedCities()) {
					writer.print(" " + connected.getName());
				}
				writer.println();
			}
		}
		writer.println(SEPARATOR);
		writer.flush();
	}

	// if case of errors: add .toString() for each integer
	public void printPlayers(List<Player> players) {
		int index = 1;
		for (Player player : players) {
			writer.println(
					SEPARATOR + "\n| Player " + index + "\n" + "| Victory Points: "
							+ player.getVictoryPoints().getAmount() + " Coins: " + player.getCoins().getAmount()
							+ " Assistants: " + player.getAssistants().getAmount() + "\n" + "| Cards in hand: "
							+ player.getPoliticCard().size() + "Nobility: " + player.getNoblePoints().getAmount());
		}
		writer.println(SEPARATOR);
	}

	private void printBonus(City city) {
		writer.print("(");
		if (city.isCapital()) {
			writer.print("Capital");
		} else {
			if (city.getNumberOfEmporium() > 0) {
				for (Bonus b : city.getReward().getGeneratedRewards()) {
					writer.print(b.getTagName().substring(0, 1).toUpperCase() + b.getAmount());
				}
			} else {
				writer.print("???");
			}
		}
		writer.print(") ");
	}


	@Override
	public void printAskPlayersNumber(int max) {
		writer.println("Choose the max number of players");
		writer.flush();
	}

	@Override
	public void printAskWhichMapToUse() {
		writer.println("Choose the number of the map:");
		/*for (int i = 0; i < maps.size(); i++) {
			writer.println((i+1) + ". " + maps.get(i));
		}*/
		writer.flush();
	}

	@Override
	public void printAskWhatActionToDo() {
		writer.println("Type in the action that you want to do");
		writer.flush();
	}

	@Override
	public void printAskPlayerName() {
		writer.println("What's your name?");
		writer.flush();
	}

	@Override
	public void printIllegalAction() {
		writer.println("There's an error with the action");
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

	}

	@Override
	public void showRoom() {
		writer.println("\nThe game will start soon");
		writer.flush();
	}

	public static void main(String[] args){
		new Cli().printCouncil();;
		
	}

	@Override
	public void playerJoined(Player p) {
		writer.println(p.getName()+ " joined the game");
	}

	@Override
	public void setAllPlayers(List<Player> players) {
		writer.println("Players list:");
		for(int i=0; i<players.size();i++){
			writer.println((i+1)+". "+players.get(i).getName());
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
}
