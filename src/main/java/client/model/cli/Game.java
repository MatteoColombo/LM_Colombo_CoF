package client.model.cli;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import client.model.ModelInterface;
import client.view.Cli;
import model.Configuration;
import model.board.Board;
import model.board.Region;
import model.board.city.City;
import model.board.council.Council;
import model.exceptions.XMLFileException;
import model.player.PermissionCard;
import model.player.Player;
import model.reward.Bonus;
import model.reward.Reward;

public class Game implements ModelInterface {
	private List<CliPlayer> players;
	private Configuration config;
	private List<CliRegion> regions;
	private int myIndex;
	private Cli view;
	private List<Integer> kingAward;
	private Map<String, Integer> colorReward;
	private List<String> kingCouncil;

	public Game(Cli view) {
		this.view = view;
		this.players = new ArrayList<>();
		this.regions = new ArrayList<>();
		this.kingAward = new ArrayList<>();
		this.kingCouncil = new ArrayList<>();
		this.colorReward = new HashMap<>();
	}

	@Override
	public void initMap(int choosen) {
		Board board;
		try {
			board = new Board(config, choosen);
			regions = new ArrayList<>();
			for (Region r : board.getRegions()) {
				List<CliCity> cities = new ArrayList<>();
				for (City c : r.getCities()) {
					List<String> connections = new ArrayList<>();
					for (City conn : c.getConnectedCities())
						connections.add(conn.getName());
					cities.add(new CliCity(c.getName(), connections, c.isCapital()));
				}
				regions.add(new CliRegion(regions.size(), cities,config.getNumberDisclosedCards()));
			}
		} catch (XMLFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getMyIndex() {
		return this.myIndex;
	}

	@Override
	public void isYourTurn() {
		view.printMessage("It's your turn!");
		view.printCities(regions);
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
	}

	@Override
	public void playerJoined(Player p) {
		CliPlayer player = new CliPlayer(p, config);
		players.add(player);
		view.printMessage(player.getName() + " joined.");
	}

	@Override
	public void setConfiguration(Configuration config) {
		this.config = config;
	}

	@Override
	public Configuration getConfiguration() {
		return this.config;
	}

	public List<Integer> getKingAward() {
		return kingAward;
	}

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
	 * @return
	 */
	public List<CliRegion> getRegions() {
		return this.regions;
	}

	/**
	 * Returns the king's council
	 * @return
	 */
	public List<String> getKingCouncil(){
		return this.kingCouncil;
	}
	
	public void buildEmporium(String city, String name){
		for(CliRegion region: regions)
			for(CliCity c: region.getCities())
				if(c.getName().equals(city)){
					
					return;
				}
	}
}
