package server.control;

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
import server.Server;
import server.control.connection.ClientInt;
import server.control.dialogue.notify.NotifyGameLoading;
import server.control.dialogue.notify.NotifyGameStarted;
import server.control.dialogue.update.NotifyKingLocation;
import server.control.dialogue.update.NotifyPlayerJoined;
import server.control.dialogue.update.NotifyPlayersList;
import server.control.dialogue.update.NotifyUpdatePlayer;
import server.control.dialogue.update.UpdateBoardRewards;
import server.control.dialogue.update.UpdateCouncil;
import server.control.dialogue.update.UpdateEmporiumBuilt;
import server.control.dialogue.update.UpdateRegionPermission;
import server.control.dialogue.update.UpdateSendCityBonus;
import server.model.Game;
import server.model.GameListener;
import server.model.TurnManager;
import server.model.action.IllegalActionException;
import server.model.board.BoardRewardsManager;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.market.OnSaleItem;
import server.model.market.Soldable;
import server.model.player.Assistants;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;
import server.model.reward.Bonus;
import server.model.reward.Reward;

public class Controller implements GameListener{
	private Game game;
	private CliParser parser;
	private ActionBuilder builder;
	private Map<ClientInt, Player> playersMap = new HashMap<>();
	private Logger logger = Logger.getGlobal();
	private Configuration config;
	private int gameMap;
	private static final String WRONGSELECTION= "Wrong selection!";
	private static final String NOTENOUGHASSISTANTS= "You don't have enough assistants!";
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
					temp.notify(new NotifyPlayerJoined(playersMap.get(client)));
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage(), e);
				playersMap.get(temp).setSuspension(true);
			}
		}
	}

	
	/**
	 * parser for the requested item to be sold.
	 * the string must be in the following format: <br>
	 * [Object to sell] [amount/index] [price] <br>
	 * e.g: politic 3 2, assistant 1 2 <br>
	 * in case of index, it must start from 1
	 * @param item the string representation of this item
	 * @param client the seller
	 */
	public void parseItemToSell(String item, ClientInt client) {
		if ("end".equals(item)) {
			game.getMarket().playerWantsToStop();
			return;
		}
		String[] parameters = item.split(" ");
		Soldable itemOnSale;
		try {
			if(parameters.length<3)
				throw new IllegalActionException("wrong parameters!");
			String object = parameters[0];
			int index = Integer.parseInt(parameters[1]);
			int price = Integer.parseInt(parameters[2]);
			if (index < 0 || price < 0)
				throw new IllegalActionException("Wrong parameters");
			switch (object) {
			case "permission":
				if (playersMap.get(client).getPermissionCard().size() < index)
					throw new IllegalActionException(NOTENOUGHASSISTANTS);
				itemOnSale = playersMap.get(client).getPermissionCard().remove(index-1);
				break;
			case "politic":
				if (playersMap.get(client).getPoliticCard().size() < index)
					throw new IllegalActionException(NOTENOUGHASSISTANTS);
				itemOnSale = playersMap.get(client).getPoliticCard().remove(index-1);
				break;
			case "assistant":
				if (playersMap.get(client).getAssistants().getAmount() < index)
					throw new IllegalActionException(NOTENOUGHASSISTANTS);
				itemOnSale = new Assistants(index);
				playersMap.get(client).getAssistants().decreaseAmount(index);
				break;
			default:
				throw new IllegalActionException(WRONGSELECTION);
			}
			game.getMarket().addItemOnSale(itemOnSale, price, playersMap.get(client));
			updatePlayers(playersMap.get(client), game.getPlayers().indexOf(playersMap.get(client)));
		} catch (IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(e);
			return;
		}

	}

	/**
	 * Receives the choosen item to buy, checks if can buy it
	 * The received string must be a simple integer 
	 * @param items
	 * @param itemIndex
	 * @param client
	 */
	public void parseItemToBuy(List<OnSaleItem> items, String itemIndex, ClientInt client) {
		if ("end".equals(itemIndex)) {
			game.getMarket().playerWantsToStop();
			return;
		}
		try {
			int index = Integer.parseInt(itemIndex);
			if (index > items.size())
				throw new IllegalActionException("Illegal index");
			if (playersMap.get(client).getCoins().getAmount() < items.get(index - 1).getPrice())
				throw new IllegalActionException("Not enough money");
			OnSaleItem solditem = items.get(index - 1);
			game.getMarket().buyItem(solditem, playersMap.get(client));
			updatePlayers(playersMap.get(client), game.getPlayers().indexOf(playersMap.get(client)));
			updatePlayers(solditem.getOwner(), game.getPlayers().indexOf(solditem.getOwner()));
		} catch (NumberFormatException | IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException(e.getMessage()));
		}
	}
	/**
	 * Send a {@link NotifyGameStarted} Dialogue to all the connected Clients.
	 * this must be called at the beginning of the game, but after the model's dialogs
	 * If a Client is not connects, it get suspended immeidately
	 */
	public void notifyGameStarted() {
		Set<ClientInt> clients = playersMap.keySet();
		for (ClientInt temp : clients) {
			try {
				temp.notify(new NotifyGameStarted());
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
			if (players > config.getMaxNumberOfPlayer() || players < 2)
				throw new IllegalActionException("invalid number of players!");
			map = Integer.parseInt(choosenMap);
			if (map > config.getMaps().size() || map < 1)
				throw new IllegalActionException("invalid map number!");
		} catch (NumberFormatException | IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException(e.getMessage()));
			client.askConfiguration(config.getMaxNumberOfPlayer());
			return;
		}
		this.gameMap = map - 1;
		setMaxNumberOfPlayers(players);
		setChoosenMap(gameMap);
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
			client.notify(new NotifyPlayersList(game.getPlayers()));
			client.notify(new NotifyGameLoading(this.gameMap));
		} catch (IOException e) {
			logger.log(Level.INFO, e.getMessage(), e);
		}
	}

	/**
	 * translate a string action request from the client into its object
	 * representation It notify the client if something went wrong. <br>
	 * The string must respect the following convention:
	 * [ACTION] [OPTIONS]
	 * <br> If one index or more is required as an option argument, it must start from 1
	 * <br>
	 * eg:
	 * <li>slide -council 1 -color pink <br>
	 * <li>emporium -city castrum -permission 1 <br>
	 * <li>extra <br>
	 * <li>king -city arkon -cards 1 2 3 
	 * <li>permission -region 2 -slot 1 -cards 1 3 5
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
				updateCouncil(cmd.getOptionValue(CliParser.OPTCOUNCIL));
				break;
			case "assistant":
				tm.performAction(builder.makeABuyAssistant(player));
				break;
			case "extra":
				tm.performAction(builder.makeAExtraMainAction(player));
				break;
			case "secondarySlide":
				tm.performAction(builder.makeASlideCouncilWithAssistant(player, cmd));
				updateCouncil(cmd.getOptionValue(CliParser.OPTCOUNCIL));
				break;
			case "shuffle":
				tm.performAction(builder.makeAShufflePermissionCards(player, cmd));
				int intRegion = Integer.parseInt(cmd.getOptionValue(CliParser.OPTREGION)) - 1;
				for (int i = 0; i < game.getBoard().getRegion(intRegion).getPermissionSlotsNumber(); i++)
					notifySendPermission(game.getBoard().getRegion(intRegion).getPermissionCard(i), intRegion, i);
				break;
			case "permission":
				tm.performAction(builder.makeABuyPermissionCard(player, cmd));
				int region = Integer.parseInt(cmd.getOptionValue(CliParser.OPTREGION)) - 1;
				int slot = Integer.parseInt(cmd.getOptionValue(CliParser.OPTPERMISSION)) - 1;
				notifySendPermission(game.getBoard().getRegion(region).getPermissionCard(slot), region, slot);
				break;
			case "emporium":
				tm.performAction(builder.makeABuildEmporium(player, cmd));
				sendEmporium(game.getPlayers().indexOf(player), cmd.getOptionValue(CliParser.OPTCITY));
				break;
			case "king":
				tm.performAction(builder.makeABuildEmporiumWithKing(player, cmd));
				sendEmporium(game.getPlayers().indexOf(player), cmd.getOptionValue(CliParser.OPTCITY));
				sendKingLocation(cmd.getOptionValue(CliParser.OPTCITY));
				break;
			case "end":
				tm.performAction(builder.makeAEndTurn(player, tm));
				break;
			default:
				throw new IllegalActionException("Unrecognized action");
			}
			if ("emporium".equals(args[0]) || "king".equals(args[0]))
				updateBoardRewards();
			updatePlayers(player, playerIndex);

		} catch (IllegalActionException | ParseException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException(e.getMessage()));
			client.askPlayerWhatActionToDo();
		}
	}
	/**
	 * send a {@link NotifyUpdatePlayer} to all clients connected to the game,
	 * when the player's parameter change in the model.
	 * If the client is disconnected it is immediately suspended
	 * 
	 * @param player
	 * @param playerIndex
	 */
	private void updatePlayers(Player player, int playerIndex) {
		Set<ClientInt> clients = playersMap.keySet();
		Player simplifiedClone = new Player(player);
		for (ClientInt client : clients) {
			try {
				client.notify(new NotifyUpdatePlayer(simplifiedClone, playerIndex));
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
					if (city.getName().equalsIgnoreCase(cityName) && !city.hasEmporiumOfPlayer(player))
						throw new IllegalActionException("You don't have an emporium in the city");
					else if (city.getName().equalsIgnoreCase(cityName) && city.hasEmporiumOfPlayer(player))
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
			updatePlayers(player, game.getPlayers().indexOf(player));
		} catch (IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(e);
			client.askCityToGetNobilityReward(cities.size());
		}

	}
	/**
	 * Parse the selected permission card, when the special
	 * take the permission reward occurse.
	 * @param card the index of the card starting from 1
	 * @param client the active player
	 * @throws IOException
	 */
	public void parseRewardOfPermissionCard(String card, ClientInt client) throws IOException {
		try {
			int index = Integer.parseInt(card);
			Player player = playersMap.get(client);
			if (index > player.getPermissionCard().size() || index < 1)
				throw new IllegalActionException(WRONGSELECTION);
			player.getPermissionCard().get(index - 1).getCardReward().assignBonusTo(playersMap.get(client));
			updatePlayers(player, game.getPlayers().indexOf(player));
		} catch (IllegalActionException | NumberFormatException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException(WRONGSELECTION));
			client.askSelectRewardOfPermissionCard();
		}
	}
	/**
	 * Parse the selected permission card when a player can take a free permission from the available slots
	 * @param card the indexes of region and slot (both starting from 1)
	 * @param client
	 * @throws IOException
	 */
	public void parseBonusFreePermissionCard(String card, ClientInt client) throws IOException {
		String[] parameters = card.split(" ");
		Player player = playersMap.get(client);
		try {
			int region = Integer.parseInt(parameters[0]);
			int index = Integer.parseInt(parameters[1]);
			if (index > config.getNumberDisclosedCards() || index < 1 || region < 1
					|| region > game.getBoard().getRegions().size())
				throw new IllegalActionException("Out of bound");
			player.getPermissionCard().add(game.getBoard().getRegion(region - 1).givePermissionCard(index - 1));
			notifySendPermission(game.getBoard().getRegion(region - 1).getPermissionCard(index - 1), region - 1,
					index - 1);
			updatePlayers(player, game.getPlayers().indexOf(player));

		} catch (NumberFormatException | IllegalActionException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			client.notifyIllegalAction(new IllegalActionException(WRONGSELECTION));
			client.askSelectRewardOfPermissionCard();
		}
	}
	/**
	 * Send all the Reward in the cities to the client as a List
	 * If a client is disconnected it got suspended
	 */
	public void notifySendCityRewards() {
		List<Reward> rewards = new ArrayList<>();
		List<City> cities = game.getBoard().getMap().getCitiesList();
		for (City c : cities)
			rewards.add(c.getReward());
		Set<ClientInt> clients = playersMap.keySet();
		for (ClientInt client : clients) {
			try {
				client.notify(new UpdateSendCityBonus(rewards));
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
				playersMap.get(client).setSuspension(true);
			}
		}
	}

	/**
	 * parse the council to update, and call notifySendCouncil.
	 */
	public void updateCouncil(String index) {
		if ("k".equals(index))
			notifySendCouncil(game.getBoard().getKingCouncil(), -1);
		else {
			int i = Integer.parseInt(index);
			notifySendCouncil(game.getBoard().getRegionCouncil(i - 1), i - 1);
		}
	}

	/**
	 * send a {@link UpdateCouncil} to all the clients
	 * @param council the council to send
	 * @param index The council index. By convention, the king has index -1
	 */
	public void notifySendCouncil(Council council, int index) {
		Council copy = new Council(council);
		Set<ClientInt> clients = playersMap.keySet();
		for (ClientInt client : clients) {
			try {
				client.notify(new UpdateCouncil(copy, index));
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
				playersMap.get(client).setSuspension(true);
			}
		}
	}

	/**
	 * send all councils one by one to the clients
	 */
	public void notifySetAllCouncil() {

		Council kingCouncil = game.getBoard().getKingCouncil();
		notifySendCouncil(kingCouncil, -1);

		List<Region> regions = game.getBoard().getRegions();
		for (int i = 0; i < regions.size(); i++) {
			notifySendCouncil(regions.get(i).getCouncil(), i);
		}
	}
	
	/**
	 * send all permissions one by one to the client
	 */
	public void notifySetAllPermissions() {
		List<Region> regions = game.getBoard().getRegions();
		for (int i = 0; i < regions.size(); i++) {

			Region r = regions.get(i);
			for (int j = 0; j < r.getPermissionSlotsNumber(); j++) {

				notifySendPermission(r.getPermissionCard(j), i, j);
			}
		}
	}

	/**
	 * send a permission card to the client
	 * @param card the permission card to send
	 * @param region the region index
	 * @param slot the region slot index
	 */
	public void notifySendPermission(PermissionCard card, int region, int slot) {
		Set<ClientInt> clients=playersMap.keySet();
		for (ClientInt client : clients) {
			try {
				client.notify(new UpdateRegionPermission(card, region, slot));
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
				playersMap.get(client).setSuspension(true);
			}
		}
	}
	/**
	 * send a {@link UpdateEmporiumBuilt} to all the clients
	 * @param player the player who bult the emporium
	 * @param city the city where the emporium is built in
	 */
	public void sendEmporium(int playerIndex, String city) {
		Set<ClientInt> clients=playersMap.keySet();
		for (ClientInt client : clients) {
			try {
				client.notify(new UpdateEmporiumBuilt(playerIndex, city));
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
				playersMap.get(client).setSuspension(true);
			}
		}
	}
	/**
	 * send a {@link NotifyKingLocation}  to the clients
	 * @param city the new city where the king is placed
	 */
	public void sendKingLocation(String city) {
		Set<ClientInt> clients = playersMap.keySet();
		for (ClientInt client : clients) {
			if (!playersMap.get(client).getSuspended())
				try {
					client.notify(new NotifyKingLocation(city));
				} catch (IOException e) {
					logger.log(Level.WARNING, e.getMessage(), e);
					playersMap.get(client).setSuspension(true);
				}
		}
	}
	/**
	 * send all the board rewards to the clients
	 */
	public void updateBoardRewards() {
		Set<ClientInt> clients = playersMap.keySet();
		BoardRewardsManager manager = game.getBoard().getBoardRewardsManager();
		List<BVictoryPoints> copyKing= new ArrayList<>();
		for(BVictoryPoints king: manager.getRemainingBoardKingRewards())
			copyKing.add(new BVictoryPoints(king.getAmount()));
		List<BoardRegionReward> copyRegion= new ArrayList<>();
		for(BoardRegionReward region: manager.getRemainingBoardRegionRewards())
			copyRegion.add(region.newCopy());
		List<BoardColorReward> copyColor= new ArrayList<>();
		for(BoardColorReward color: manager.getRemainingBoardColorRewards())
			copyColor.add(color.newCopy());
		
		for (ClientInt client : clients) {
			if (!playersMap.get(client).getSuspended()) {
				try {
					client.notify(new UpdateBoardRewards(copyKing,
							copyColor, copyRegion));
				} catch (IOException e) {
					logger.log(Level.WARNING, e.getMessage(), e);
					playersMap.get(client).setSuspension(true);
				}
			}
		}
	}

	@Override
	public void gameEnded(Game game) {
		this.playersMap.clear();
		this.playersMap=null;
		this.game=null;
		this.logger=null;
		this.config=null;
		this.parser=null;
		this.builder=null;
	}
	
}
