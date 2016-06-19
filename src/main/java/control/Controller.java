package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import model.Configuration;
import model.Game;
import model.TurnManager;
import model.board.city.City;
import model.exceptions.ConfigurationErrorException;
import model.exceptions.IllegalActionException;
import model.market.OnSaleItem;
import model.market.Soldable;
import model.player.Assistants;
import model.player.Player;
import model.reward.Bonus;
import model.reward.Reward;
import server.Server;
import view.server.ClientInt;

public class Controller {
	private Game game;
	private CliParser parser;
	private ActionBuilder builder;
	private Map<ClientInt, Player> playersMap = new HashMap<>();
	private Logger logger = Logger.getGlobal();
	private Configuration config;

	public Controller(Game game, Configuration config) {
		this.game = game;
		this.config = config;
		this.parser = new CliParser();
	}

	/**
	 * Notifies all the players that a new one joined the game
	 * 
	 * @param client
	 */
	public void notifyPlayerJoined(ClientInt client) {
		Set<ClientInt> clients = playersMap.keySet();
		for (ClientInt temp : clients) {
			try {
				if (client != temp)
					temp.notifyPlayerJoined(playersMap.get(client));
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage(), e);
				playersMap.get(temp).setSuspension(true);
			}
		}
	}

	public void parseItemToSell(String item, ClientInt client) {
		String[] parameters = item.split(" ");
		Soldable itemOnSale;
		try {
			String object = parameters[0];
			int index = Integer.parseInt(parameters[1]);
			int price = Integer.parseInt(parameters[2]);
			if (index < 0 || price < 0)
				throw new IllegalActionException("Wrong parameters");
			switch (object) {
			case "permit":
				if (playersMap.get(client).getPermissionCard().size() < index)
					throw new IllegalActionException("You dont have enough assistants");
				if (playersMap.get(client).getPermissionCard().get(index).getIfCardUsed())
					throw new IllegalActionException("Card is already used, can't be sold");
				itemOnSale = playersMap.get(client).getPermissionCard().remove(index);
				break;
			case "politic":
				if (playersMap.get(client).getPoliticCard().size() < index)
					throw new IllegalActionException("You dont have enough assistants");
				itemOnSale = playersMap.get(client).getPoliticCard().remove(index);
				break;
			case "assistant":
				if (playersMap.get(client).getAssistants().getAmount() < index)
					throw new IllegalActionException("You dont have enough assistants");
				itemOnSale = new Assistants(index);
				playersMap.get(client).getAssistants().decreaseAmount(index);
				break;
			default:
				throw new IllegalActionException("Wrong selection");
			}
			game.getMarket().addItemOnSale(itemOnSale, price, playersMap.get(client));
		} catch (IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(e);
			return;
		}

	}

	/**
	 * Receives the choosen item to buy, checks if can buy it
	 * 
	 * @param items
	 * @param itemIndex
	 * @param client
	 */
	public void parseItemToBuy(List<OnSaleItem> items, String itemIndex, ClientInt client) {
		try {
			int index = Integer.parseInt(itemIndex);
			if (index > items.size())
				throw new IllegalActionException("Illegal index");
			if (playersMap.get(client).getCoins().getAmount() < items.get(index).getPrice())
				throw new IllegalActionException("Not enough money");
			game.getMarket().buyItem(items.get(index), playersMap.get(client));
		} catch (NumberFormatException | IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException(e.getMessage()));
		}
	}

	public void notifyGameStarted() {
		Set<ClientInt> clients = playersMap.keySet();
		for (ClientInt temp : clients) {
			try {
				temp.notifyGameStarted();
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage(), e);
				playersMap.get(temp).setSuspension(true);
			}
		}
	}

	/**
	 * Tells the game to launch it's configuration method and then, when the
	 * configuration is completed, puts the game in a loading status where i can
	 * listen for other players
	 * 
	 * @throws ConfigurationErrorException
	 */
	public void configGame() throws ConfigurationErrorException {
		game.configGame();
		Server.acceptPlayers(game);
	}

	/**
	 * Parses the game configuration and then updates the model
	 * 
	 * @param gameConfigMessage
	 *            this is the string which containts the configuration
	 * @param client
	 *            this is the client who is configuring; it is used in case of
	 *            wrong config
	 * @throws IOException
	 */
	public void parseGameConfiguration(String maxPlayers, String choosenMap, ClientInt client) throws IOException {
		int map = 0;
		int players = config.getMaxNumberOfPlayer();
		try {
			players = Integer.parseInt(maxPlayers);
			if (players > config.getMaxNumberOfPlayer() || players < 0)
				throw new IllegalActionException("too many players");
			map = Integer.parseInt(choosenMap);
			if (map > config.getMaps().size() || map < 0)
				throw new IllegalActionException("too many players");
		} catch (NumberFormatException | IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException(e.getMessage()));
			client.askConfiguration(config.getMaxNumberOfPlayer());
			return;
		}
		setMaxNumberOfPlayers(players);
		setChoosenMap(map);
	}

	/**
	 * Sets the max number of player that connect to a game
	 * 
	 * @param maxNumberOfPlayers
	 *            the max number of players
	 */
	private void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
		game.setMaxNumberOfPlayers(maxNumberOfPlayers);
	}

	/**
	 * Sets the number of the choosen map in the game
	 * 
	 * @param choosenMap
	 *            the number of the choosen map in the maps' list
	 */
	private void setChoosenMap(int choosenMap) {
		game.setChoosenMap(choosenMap);
	}

	/**
	 * Notifies the game to generate a player and then adds it with it's client
	 * to the Player-ClientInt hashmap
	 * 
	 * @param client
	 *            the client who is used to generate a player
	 */
	public void addPlayer(ClientInt client) {
		playersMap.put(client, game.addPlayer(client));
	}

	public void notifyGameLoading(ClientInt client) {
		try {
			client.sendPlayersList(game.getPlayers());
			client.notifyGameLoading();
		} catch (IOException e) {
			logger.log(Level.INFO, e.getMessage(), e);
		}
	}

	/**
	 * translate a string action request from the client into its object
	 * representation It notify the client if something went wrong
	 * 
	 * @param s
	 *            the input from the cli
	 * @throws IOException
	 */
	public void performAction(ClientInt client, String s) throws IOException {
		this.builder = new ActionBuilder(game.getBoard(), config);
		int playerIndex = game.getPlayers().indexOf(playersMap.get(client));
		Player player = playersMap.get(client);
		String[] args = s.split(" ");
		try {
			CommandLine cmd = parser.computeRequest(args);
			TurnManager tm = game.getTurnManager();

			switch (args[0]) {
			case "slide":
				tm.performAction(builder.makeASlideCouncil(player, cmd));
				updatePlayers(player, playerIndex);
				return;
			case "assistant":
				tm.performAction(builder.makeABuyAssistant(player));
				return;
			case "extra":
				tm.performAction(builder.makeAExtraMainAction(player));
				return;
			case "secondarySlide":
				tm.performAction(builder.makeASlideCouncilWithAssistant(player, cmd));
				return;
			case "shuffle":
				tm.performAction(builder.makeAShufflePermissionCards(player, cmd));
				return;
			case "permission":
				tm.performAction(builder.makeAShufflePermissionCards(player, cmd));
				return;
			case "emporium":
				tm.performAction(builder.makeABuildEmporium(player, cmd));
				return;
			case "king":
				tm.performAction(builder.makeABuildEmporiumWithKing(player, cmd));
				return;
			case "end":
				tm.performAction(builder.makeAEndTurn(player, tm));
				return;
			default:
				throw new IllegalActionException("Unrecognized action");
			}

		} catch (IllegalActionException | ParseException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException(e.getMessage()));
			client.askPlayerWhatActionToDo();
		}
	}

	private void updatePlayers(Player player, int playerIndex) {
		Set<ClientInt> clients = playersMap.keySet();
		Player simplifiedClone = new Player(player);
		for (ClientInt client : clients) {
			try {
				client.updatePlayer(simplifiedClone, playerIndex);
			} catch (IOException e) {
				player.setSuspension(true);
				logger.log(Level.WARNING, e.getMessage(), e);
			}

		}
	}

	/**
	 * This is the method that checks if the city (or the cities) selcted for
	 * the nobility bonus are eligible for the reward and in case it gives them
	 * to the player
	 * 
	 * @param cities
	 *            the names of the cities
	 * @param client
	 * @throws IOException
	 */
	public void parseBonusGetCityBonus(List<String> cities, ClientInt client) throws IOException {
		Player player = playersMap.get(client);
		List<City> allCitiesOfMap = game.getBoard().getMap().getCitiesList();
		List<Reward> reward = new ArrayList<>();
		try {
			for (String cityName : cities) {
				for (City city : allCitiesOfMap)
					if (city.getName().equals(cityName) && !city.hasEmporiumOfPlayer(player))
						throw new IllegalActionException("You don't have an emporium in the city");
					else if (city.getName().equals(cityName) && city.hasEmporiumOfPlayer(player))
						reward.add(city.getReward());
			}
			for (Reward r : reward) {
				for (Bonus bonus : r.getGeneratedRewards()) {
					if ("nobility".equals(bonus.getTagName()))
						throw new IllegalActionException("You can't select a city which has a nobility bonus");
				}
			}
			for (Reward r : reward)
				r.assignBonusTo(player);
		} catch (IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(e);
			client.askCityToGetNobilityReward(cities.size());
		}

	}

	public void parseRewardOfPermissionCard(String card, ClientInt client) throws IOException{
		try{
			int index= Integer.parseInt(card);
			if(index > playersMap.get(client).getPermissionCard().size() || index < 1)
				throw new IllegalActionException("Out of bound");
			playersMap.get(client).getPermissionCard().get(index-1).getCardReward().assignBonusTo(playersMap.get(client));
		}catch(IllegalActionException | NumberFormatException e){
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException("wrong selection"));
			client.askSelectRewardOfPermissionCard();
		} 
	}

	public void parseBonusFreePermissionCard(String card, ClientInt client) throws IOException {
		String[] parameters= card.split(" ");
		try{
			int region= Integer.parseInt(parameters[0]);
			int index= Integer.parseInt(parameters[1]);
			if(index > config.getNumberDisclosedCards() || index < 1 || region < 1 || region > game.getBoard().getRegions().size())
				throw new IllegalActionException("Out of bound");
			playersMap.get(client).getPermissionCard().add(game.getBoard().getRegion(region).givePermissionCard(index));
		}catch(NumberFormatException | IllegalActionException e){
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException("wrong selection"));
			client.askSelectRewardOfPermissionCard();
		}
	}

}
