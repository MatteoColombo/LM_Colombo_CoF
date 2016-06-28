package client.viewGUI.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import client.model.ModelInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import server.model.board.Board;
import server.model.board.council.Council;
import server.model.configuration.Configuration;
import server.model.configuration.XMLFileException;
import server.model.market.OnSaleItem;
import server.model.player.Assistants;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;
import server.model.reward.Reward;

public class GameProperty implements ModelInterface {
	/**
	 * This is the list of the available colors for each game. 
	 * Each player have a color assigned to himself to distinguish from the others.
	 */
	private static List<Color> playersColors = new LinkedList<Color>(Arrays.asList(Color.ROYALBLUE, Color.CRIMSON, Color.GREEN,  Color.YELLOW,
			Color.PURPLE, Color.WHITE, Color.ORANGE, Color.GRAY, Color.AQUA, Color.BROWN));

	private ObservableList<PlayerProperty> players = FXCollections.observableArrayList();
	private SimpleMap map;
	private int myIndex;
	private Configuration config;
	
	private ObservableList<ItemProperty> market = FXCollections.observableArrayList();

	@Override
	public void initMap(int choosen) {
		try {
			Board b = new Board(config, choosen);
			this.map = new SimpleMap(b);
		} catch (XMLFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO call the garbage collector
	}

	public PlayerProperty getMyPlayerData() {
		return players.get(myIndex);
	}

	public ObservableList<PlayerProperty> getPlayers() {
		return this.players;
	}

	public SimpleMap getMap() {
		return this.map;
	}

	@Override
	public int getMyIndex() {
		return myIndex;
	}

	@Override
	public void isYourTurn() {
		players.get(myIndex).canNotDoMainAction().set(false);
		players.get(myIndex).canNotDoSideAction().set(false);
	}

	@Override
	public void yourTurnEnded() {
		players.get(myIndex).canNotDoMainAction().set(true);
		players.get(myIndex).canNotDoSideAction().set(true);
	}

	@Override
	public void setAllPlayers(List<Player> players) {
		this.players.clear();
		myIndex = players.size() - 1;
		for (Player p : players) {
			playerJoined(p);
		}
	}

	@Override
	public void updatePlayer(Player p, int index) {
		players.get(index).setAll(p);
		
	}

	@Override
	public void playerJoined(Player p) {
		PlayerProperty newPlayer = new PlayerProperty().setAllButPermissions(p);
		newPlayer.setColor(playersColors.remove(0));
		players.add(newPlayer);
	}
	
	@Override
	public void setConfiguration(Configuration config){
		this.config=config;
	}
	
	@Override
	public Configuration getConfiguration(){
		return config;
	}


	@Override
	public void setCouncil(Council council, int index) {
		this.getMap().setCouncil(council, index);
	}

	@Override
	public void setBonus(List<Reward> bonus) {
		this.getMap().setCityRewards(bonus);
	}

	@Override
	public void setPermission(PermissionCard pc, int region, int slot) {
		this.getMap().getRegions().get(region).getPermissions()[slot].set(pc);
	}

	@Override
	public void buildEmporium(String city, String name) {
		Color playerColor = null;
		for(PlayerProperty p: players) {
			if(p.getName().equals(name)) {
				playerColor = p.getColor();
				break;
			}
		}
		
		for(SimpleRegion sr: map.getRegions()) {
			for(SimpleCity sc: sr.getCities()) {
				if(sc.getName().equalsIgnoreCase(city)) {
					sc.getEmporiums().add(playerColor);
				}
			}
		}
	}

	@Override
	public void setKingLocation(String location) {
		for(SimpleRegion region:map.getRegions())
			for(SimpleCity city: region.getCities()){
				city.setHasKing(false);
				if(city.getName().equalsIgnoreCase(location))
					city.setHasKing(true);
			}
	}

	@Override
	public void updateBoardReward(List<BVictoryPoints> kingReward, List<BoardColorReward> colorReward,
			List<BoardRegionReward> regionReward) {
		for(int i = 0; i<regionReward.size();i++)
			map.getRegions().get(i).setCounquerBonus(regionReward.get(i).getBRBonus().getAmount());
		map.updateColorReward(colorReward);
		map.updateKingBonus(kingReward);
			
	}
	
	public void setMarket(List<OnSaleItem> items) {
		market.clear();
		for(OnSaleItem item: items) {
			market.add(new ItemProperty(item));
		}
	}
	
	public ObservableList<ItemProperty> getMarket() {
		return this.market;
	}
}
