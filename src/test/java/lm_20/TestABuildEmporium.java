package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import server.model.action.ABuildEmporium;
import server.model.action.ABuildEmporiumWithKing;
import server.model.action.IllegalActionException;
import server.model.board.BoardRewardsManager;
import server.model.board.King;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.council.CouncilorPool;
import server.model.board.map.MapExplorer;
import server.model.board.map.MapLoader;
import server.model.board.map.MapXMLFileException;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.TrackXMLFileException;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;
import server.model.reward.BAssistants;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;
import server.model.reward.BoardReward;
import server.model.reward.Reward;

public class TestABuildEmporium {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
	private List<BoardColorReward> bColorRewards;
	private List<BoardRegionReward> bRegionRewards;
	private List<BVictoryPoints> bKingRewards;
	private BoardRewardsManager bRewardManager;
	private List<City> allMapCities;
	MapExplorer mExplorer;
	MapLoader mLoader;
	private Configuration config;

	@Before
	public void setUp() throws MapXMLFileException, TrackXMLFileException, ConfigurationErrorException {
		config= new Configuration();
		colorList = config.getColorsList();
		this.player= new Player(config, new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack()));
		player.getCoins().increaseAmount(10);
		player.getAssistants().increaseAmount(3);
		this.pool = new CouncilorPool(4, 4, colorList);
		bColorRewards = new ArrayList<>();
		bKingRewards = new ArrayList<>();
		bRegionRewards = new ArrayList<>();
		bColorRewards.add(new BoardColorReward(Color.decode("#008000"), 35));
		bColorRewards.add(new BoardColorReward(Color.decode("#2268df"), 15));
		bColorRewards.add(new BoardColorReward(Color.decode("#ffd700"), 50));
		bColorRewards.add(new BoardColorReward(Color.decode("#f44343"), 25));
		this.mLoader = new MapLoader("src/main/resources/map.xml", pool);
		bRegionRewards.add(new BoardRegionReward(this.mLoader.getRegions().get(0), 25));
		bRegionRewards.add(new BoardRegionReward(this.mLoader.getRegions().get(1), 45));
		bRegionRewards.add(new BoardRegionReward(this.mLoader.getRegions().get(2), 35));
		bKingRewards.add(new BVictoryPoints(70));
		bKingRewards.add(new BVictoryPoints(55));
		bKingRewards.add(new BVictoryPoints(40));
		bKingRewards.add(new BVictoryPoints(25));
		bKingRewards.add(new BVictoryPoints(10));
		this.bRewardManager = new BoardRewardsManager(bColorRewards, bRegionRewards, bKingRewards);
		this.allMapCities = this.mLoader.getCitiesList();
		this.mExplorer = new MapExplorer();

	}

	/**
	 * Builds an emporium using a permit card, the city hasn't other emporiums
	 * after the first emporium it tries to build a second one in the same city
	 * with the same player, the action is rejected
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithPermission() throws Exception {
		PermissionCard card;
		ABuildEmporium action;
		card = new PermissionCard(this.mLoader.getRegions().get(0).getCities());
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), this.allMapCities, this.bRewardManager);
		assertEquals(true, action.isMain());
		action.execute();
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), this.allMapCities, this.bRewardManager);
	}

	/**
	 * Tries to build an emporium in a city which doesn't satisfies the permit
	 * card
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithWrongCity() throws Exception {
		PermissionCard card;
		ABuildEmporium action;
		card = new PermissionCard(this.mLoader.getRegions().get(0).getCities());
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), this.allMapCities, this.bRewardManager);
		action.execute();
		action = new ABuildEmporium(player, card, this.mLoader.getRegions().get(2).getCities().get(0),
				this.allMapCities, this.bRewardManager);
	}

	/**
	 * Tries to build an emporium in a city which already has another emporium
	 * of another player. The player has no money and the action is rejected
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithAnotherPlayerEmporium() throws Exception {
		PermissionCard card;
		ABuildEmporium action;
		card = new PermissionCard(this.mLoader.getRegions().get(0).getCities());
		Player p2= new Player(config, new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack()));
		p2.getCoins().increaseAmount(10);
		p2.getAssistants().increaseAmount(0);
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), this.allMapCities, this.bRewardManager);
		action.execute();
		action = new ABuildEmporium(p2, card, card.getCardCity().get(0), this.allMapCities, this.bRewardManager);
		action.execute();
	}

	/**
	 * Builds an emporium using the king in a different city and then tries to
	 * build another in the same
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithKing() throws Exception {
		King king;
		ABuildEmporiumWithKing action;
		king = new King(this.mLoader.getKingCity(), pool.getCouncil());
		List<Color> councilColorList = king.getKingCouncil().getCouncilorsColor();
		List<PoliticCard> pickedPCards = new ArrayList<>();
		for (Color councilorColor : councilColorList)
			pickedPCards.add(new PoliticCard(councilorColor));
		action = new ABuildEmporiumWithKing(player, king, this.mLoader.getRegions().get(0).getCities().get(0),
				this.allMapCities, pickedPCards, this.bRewardManager);
		assertEquals(true, action.isMain());
		action.execute();
		assertEquals(1, this.mLoader.getRegions().get(0).getCities().get(0).getNumberOfEmporium());
		for (Color councilorColor : councilColorList)
			pickedPCards.add(new PoliticCard(councilorColor));
		action = new ABuildEmporiumWithKing(player, king, this.mLoader.getRegions().get(0).getCities().get(0),
				this.allMapCities, pickedPCards, this.bRewardManager);

	}

	/**
	 * Builds an emporium using the king in a different city but can't afford it
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithKingNoMoney() throws Exception {
		King king;
		ABuildEmporiumWithKing action;
		this.player.getCoins().decreaseAmount(10);
		king = new King(this.mLoader.getKingCity(), pool.getCouncil());
		List<Color> councilColorList = king.getKingCouncil().getCouncilorsColor();
		List<PoliticCard> pickedPCards = new ArrayList<>();
		for (Color councilorColor : councilColorList)
			pickedPCards.add(new PoliticCard(councilorColor));
		action = new ABuildEmporiumWithKing(player, king, this.mLoader.getRegions().get(0).getCities().get(0),
				this.allMapCities, pickedPCards, this.bRewardManager);
		assertEquals(true, action.isMain());
		assertEquals(0, player.getCoins().getAmount());
		action.execute();

	}

	/**
	 * Tests an example of {@link ABuildEmporium} with all the
	 * {@link BoardReward BoardRewards}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBoardRewards() throws Exception {
		// Initialization
		PermissionCard card;
		ABuildEmporium action;
		List<City> allCities = new ArrayList<>();
		List<City> region1Cities = new ArrayList<>();
		List<City> region2Cities = new ArrayList<>();
		region1Cities.add(new City(Color.decode("#008000"), "City1", new Reward(new BAssistants(10))));
		region2Cities.add(new City(Color.decode("#2268df"), "City2", new Reward(new BAssistants(1))));
		Region region1 = new Region("Region1", region1Cities, null, 2);
		Region region2 = new Region("Region2", region2Cities, null, 2);
		region1Cities.get(0).setRegion(region1);
		region1Cities.get(0).addConnection(region2Cities.get(0));
		region2Cities.get(0).setRegion(region2);
		region2Cities.get(0).addConnection(region1Cities.get(0));
		allCities.addAll(region1Cities);
		allCities.addAll(region2Cities);
		List<BoardColorReward> newBColorRewards = new ArrayList<>();
		List<BoardRegionReward> newBRegionRewards = new ArrayList<>();
		List<BVictoryPoints> newBKingRewards = new ArrayList<>();
		newBColorRewards.add(new BoardColorReward(Color.decode("#008000"), 30));
		newBColorRewards.add(new BoardColorReward(Color.decode("#2268df"), 1));
		newBRegionRewards.add(new BoardRegionReward(region1, 20));
		newBRegionRewards.add(new BoardRegionReward(region2, 1));
		newBKingRewards.add(new BVictoryPoints(35));
		newBKingRewards.add(new BVictoryPoints(15));
		newBKingRewards.add(new BVictoryPoints(1));
		BoardRewardsManager newBRewardManager = new BoardRewardsManager(newBColorRewards, newBRegionRewards,
				newBKingRewards);
		// Test
		card = new PermissionCard(allCities, null);
		action = new ABuildEmporium(this.player, card, region1Cities.get(0), allCities, newBRewardManager);
		assertEquals(true, action.isMain());
		action.execute();
		assertEquals(100, this.player.getVictoryPoints().getAmount());
		assertEquals(13, this.player.getAssistants().getAmount());
	}
}