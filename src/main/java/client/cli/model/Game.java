package client.cli.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import client.cli.view.Cli;
import client.model.ModelInterface;
import server.model.board.Board;
import server.model.board.BoardRewardsManager;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.configuration.Configuration;
import server.model.configuration.XMLFileException;
import server.model.market.OnSaleItem;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;
import server.model.reward.Bonus;
import server.model.reward.Reward;

/**
 * This is the main class of the simplified model used for the CLI
 * 
 * @author Matteo Colombo
 *
 */
public class Game implements ModelInterface {
	private List<CliPlayer> players;
	private Configuration config;
	private List<CliRegion> regions;
	private int myIndex;
	private Cli view;
	private int kingAward;
	private Map<String, Integer> colorReward;
	private List<String> kingCouncil;
	private Logger logger = Logger.getGlobal();

	/**
	 * In
	 * 
	 * @param view
	 */
	public Game(Cli view) {
		this.view = view;
		this.players = new ArrayList<>();
		this.regions = new ArrayList<>();
		this.kingCouncil = new ArrayList<>();
		this.colorReward = new HashMap<>();
	}

	@Override
	public void initMap(int choosen) {
		Board board;
		try {
			board = new Board(config, choosen);
			for (Region r : board.getRegions()) {
				List<CliCity> cities = new ArrayList<>();
				for (City c : r.getCities())
					cities.add(new CliCity(c.getName(), c.getConnectedCities(), c.isCapital(),
							config.getCityColor().get(c.getColor())));
				regions.add(new CliRegion(regions.size(), cities, config.getNumberDisclosedCards()));
				setBoardReward(board);
			}
		} catch (XMLFileException e) {
			logger.log(Level.SEVERE, "There is an error with the configuration, please fix it!", e);
		}
	}

	/**
	 * Sets the board rewards, it calls the method which is used to update them
	 * 
	 * @param b
	 *            the board
	 */
	private void setBoardReward(Board b) {
		BoardRewardsManager manager = b.getBoardRewardsManager();
		updateBoardReward(manager.getRemainingBoardKingRewards(), manager.getRemainingBoardColorRewards(),
				manager.getRemainingBoardRegionRewards());
	}

	@Override
	public int getMyIndex() {
		return this.myIndex;
	}

	@Override
	public void isYourTurn() {
		view.printMessage("It's your turn!");
		view.printMap(regions);
		view.printPlayers(players);
	}

	@Override
	public void yourTurnEnded() {
		view.printMessage("Your turn ended!");
	}

	@Override
	public void setCouncil(Council council, int index) {
		List<String> stringCouncil = new ArrayList<>();
		for (Color c : council.getCouncilorsColor()) {
			stringCouncil.add(config.getColorsTranslationReverse().get(c));
		}
		if (index == -1)
			this.kingCouncil = stringCouncil;
		else
			this.regions.get(index).setCouncil(stringCouncil);
	}

	@Override
	public void setBonus(List<Reward> bonus) {
		int i = 0;
		for (CliRegion r : regions) {
			for (CliCity c : r.getCities()) {
				List<CliBonus> rewards = new ArrayList<>();
				if (!c.isHasKing())
					for (Bonus b : bonus.get(i).getGeneratedRewards())
						rewards.add(new CliBonus(b.getAmount(), b.getTagName()));
				c.setBonus(rewards);
				i++;
			}
		}
	}

	@Override
	public void setAllPlayers(List<Player> players) {
		this.myIndex = players.size() - 1;
		this.players.clear();
		for (Player p : players)
			playerJoined(p);
		view.printMessage("Players list:");
		for (int i = 1; i <= this.players.size(); i++) {
			view.printMessage(i + ". " + this.players.get(i - 1).getName());
		}
	}

	@Override
	public void updatePlayer(Player p, int index) {
		players.remove(index);
		CliPlayer player = new CliPlayer(p, config);
		players.add(index, player);
		view.printMap(regions);
		view.printPlayers(players);
	}

	@Override
	public void playerJoined(Player p) {
		if (!("_Server_".equals(p.getName()) && p.getAssistants().getAmount() == 0 && p.getCoins().getAmount() == 0)) {
			CliPlayer player = new CliPlayer(p, config);
			players.add(player);
			view.printMessage(player.getName() + " joined.");
		}
	}

	@Override
	public void setConfiguration(Configuration config) {
		this.config = config;
	}

	@Override
	public Configuration getConfiguration() {
		return this.config;
	}

	/**
	 * Returns the next king reward
	 * 
	 * @return an integer which represents the next king's award
	 */
	public int getKingAward() {
		return kingAward;
	}

	/**
	 * Returns the map of the city colors rewards
	 * 
	 * @return the map of the colors reward
	 */
	public Map<String, Integer> getColorReward() {
		return colorReward;
	}

	@Override
	public void setPermission(PermissionCard pc, int region, int slot) {
		List<String> cities = pc.getCardCity().stream().map(city -> city.getName()).collect(Collectors.toList());
		List<CliBonus> reward = pc.getCardReward().getGeneratedRewards().stream()
				.map(bonus -> new CliBonus(bonus.getAmount(), bonus.getTagName())).collect(Collectors.toList());
		regions.get(region).getPermission()[slot] = new CliPermission(cities, reward, pc.getIfCardUsed());
	}

	/**
	 * returns the regions of the map
	 * 
	 * @return
	 */
	public List<CliRegion> getRegions() {
		return this.regions;
	}

	/**
	 * Returns the king's council
	 * 
	 * @return
	 */
	public List<String> getKingCouncil() {
		return this.kingCouncil;
	}

	@Override
	public void buildEmporium(String city, int playerIndex) {
		for (CliRegion region : regions)
			for (CliCity c : region.getCities())
				if (c.getName().equalsIgnoreCase(city)) {
					c.addEmporium(playerIndex);
					return;
				}
	}

	@Override
	public void setKingLocation(String location) {
		for (CliRegion region : regions) {
			for (CliCity city : region.getCities()) {
				city.setHasKing(false);
				if (city.getName().equalsIgnoreCase(location))
					city.setHasKing(true);
			}
		}

	}

	@Override
	public void updateBoardReward(List<BVictoryPoints> kingReward, List<BoardColorReward> colorReward,
			List<BoardRegionReward> regionReward) {
		this.kingAward = kingReward.get(0).getAmount();
		for (int i = 0; i < regions.size(); i++)
			regions.get(i).setBonus(regionReward.get(i).getBRBonus().getAmount());
		this.colorReward.clear();
		for (BoardColorReward rew : colorReward)
			this.colorReward.put(config.getCityColor().get(rew.getBRKey()), rew.getBRBonus().getAmount());
	}

	@Override
	public void setMarket(List<OnSaleItem> items) {
		view.printMessage("Choose what item you want to buy:");
		for (int i = 1; i < items.size(); i++)
			view.printMessage(i + ". " + items.get(i - 1).printedMessage(config));
	}
}
