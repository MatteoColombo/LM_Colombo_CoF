package lm_20;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import model.action.ABuildEmporium;
import model.action.ABuildEmporiumWithKing;
import model.board.BoardRewardsManager;
import model.board.King;
import model.board.city.City;
import model.board.council.CouncilorPool;
import model.board.map.MapExplorer;
import model.board.map.MapLoader;
import model.exceptions.IllegalActionException;
import model.exceptions.MapXMLFileException;
import model.player.PermissionCard;
import model.player.Player;
import model.reward.BVictoryPoints;
import model.reward.BoardColorReward;
import model.reward.BoardRegionReward;
import model.reward.Bonus;
import model.reward.Reward;

public class TestABuildEmporium {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
	private List<BoardColorReward> bColorRewards;
	private List<BoardRegionReward> bRegionRewards;
	private List<BVictoryPoints> bKingRewards;
	private BoardRewardsManager bRewardManager;
	private List<City> allMapCities;
	private int rewardAmount;
	MapExplorer mExplorer;
	MapLoader mLoader;
	private final int ALLCITIES = 14;

	@Before
	public void setUp() throws MapXMLFileException {
		colorList = new ArrayList<Color>();
		colorList.add(Color.BLACK);
		colorList.add(Color.BLUE);
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.ORANGE);
		this.player = new Player(10, 3, 6, 10, colorList, 0, 0);
		this.pool = new CouncilorPool(4, 4, colorList);
		bColorRewards = new ArrayList<>();
		bKingRewards = new ArrayList<>();
		bRegionRewards = new ArrayList<>();
		bColorRewards.add(new BoardColorReward(Color.valueOf("#008000"), 35));
		bColorRewards.add(new BoardColorReward(Color.valueOf("#2268df"), 15));
		bColorRewards.add(new BoardColorReward(Color.valueOf("#ffd700"), 50));
		bColorRewards.add(new BoardColorReward(Color.valueOf("#f44343"), 25));
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
		this.rewardAmount = 0;
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
		Player p2 = new Player(10, 0, 6, 10, colorList, 0, 0);
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), this.allMapCities, this.bRewardManager);
		action.execute();
		action = new ABuildEmporium(p2, card, card.getCardCity().get(0), this.allMapCities, this.bRewardManager);
		action.execute();
	}

	/**
	 * Builds an emporium using the king in a different city and then tries to
	 * build another in the same
	 */
	@Ignore
	@Test(expected = IllegalActionException.class)
	public void testBuildWithKing() throws Exception {
		King king;
		ABuildEmporiumWithKing action;
		king = new King(this.mLoader.getKingCity(), pool.getCouncil());
		action = new ABuildEmporiumWithKing(player, king, this.mLoader.getRegions().get(0).getCities().get(0),
				this.allMapCities, null, this.bRewardManager);
		assertEquals(true, action.isMain());
		action.execute();
		assertEquals(1, this.mLoader.getRegions().get(0).getCities().get(0).getNumberOfEmporium());
		action = new ABuildEmporiumWithKing(player, king, this.mLoader.getRegions().get(0).getCities().get(0),
				this.allMapCities, null, this.bRewardManager);

	}

	/**
	 * Builds an emporium using the king in a differente city but can't afford
	 * it
	 */
	@Ignore
	@Test(expected = IllegalActionException.class)
	public void testBuildWithKingNoMoney() throws Exception {
		King king;
		ABuildEmporiumWithKing action;
		this.player.getCoins().decreaseAmount(3);
		king = new King(this.mLoader.getKingCity(), pool.getCouncil());
		action = new ABuildEmporiumWithKing(player, king, this.mLoader.getRegions().get(0).getCities().get(0),
				this.allMapCities, null, this.bRewardManager);
		assertEquals(true, action.isMain());
		action.execute();
		assertEquals(10, player.getCoins().getAmount());
	}

	/**
	 * Tests if the player gains the right amount of VictoryPoints using a
	 * PermissionCard
	 * 
	 */
	@Test
	public void testBoardRewards() throws Exception {
		PermissionCard card;
		ABuildEmporium action;
		card = new PermissionCard(this.mLoader.getRegions().get(0).getCities());
		action = new ABuildEmporium(this.player, card, card.getCardCity().get(0), this.allMapCities,
				this.bRewardManager);
		assertEquals(true, action.isMain());
		action.execute();
		for (Reward r : this.mExplorer.getAdiacentRewards(card.getCardCity().get(0), this.player))
			for (Bonus b : r.getGeneratedRewards())
				if (b.getTagName().equals("victory"))
					this.rewardAmount += b.getAmount();
		assertEquals(this.player.getVictoryPoints().getAmount(), this.rewardAmount);
	}

	/**
	 * Tests if the player gains the right amount of VictoryPoints after
	 * multiple Emporiums
	 * 
	 */
	@Ignore
	@Test
	public void testAdvancedBoardRewards() throws Exception {
		PermissionCard card;
		ABuildEmporium action;
		List<BoardColorReward> bColorRewardsCopy = new ArrayList<BoardColorReward>(this.bColorRewards);
		List<BoardRegionReward> bRegionRewardsCopy = new ArrayList<BoardRegionReward>(this.bRegionRewards);
		List<BVictoryPoints> bKingRewardsCopy = new ArrayList<BVictoryPoints>(this.bKingRewards);
		BoardRewardsManager bRewardManageCopy = new BoardRewardsManager(bColorRewardsCopy, bRegionRewardsCopy,
				bKingRewardsCopy);
		Random rnd = new Random();
		int rndNum = rnd.nextInt(ALLCITIES);
		List<Integer> cityNumbers = new ArrayList<Integer>();
		for (int i = 0; i <= rndNum; i++)
			cityNumbers.add(i);
		Collections.shuffle(cityNumbers);
		System.out.println("Cities that are going to by assigned to this player: " + (rndNum += 1));
		do {
			int n = cityNumbers.remove(0);
			int region, city;
			if (n <= 4) {
				region = 0;
				city = n;
			} else if (n > 4 && n <= 9) {
				region = 1;
				city = n - 5;
			} else {
				region = 2;
				city = n - 10;
			}
			card = new PermissionCard(this.mLoader.getRegions().get(region).getCities(), null);
			action = new ABuildEmporium(this.player, card, this.mLoader.getRegions().get(region).getCities().get(city),
					this.allMapCities, this.bRewardManager);
			assertEquals(true, action.isMain());
			action.execute();
			System.out.print("VPoints form cities: ");
			for (Reward r : this.mExplorer
					.getAdiacentRewards(this.mLoader.getRegions().get(region).getCities().get(city), this.player))
				for (Bonus b : r.getGeneratedRewards())
					if (b.getTagName().equals("victory")) {
						this.rewardAmount += b.getAmount();
						System.out.printf("%d + ", b.getAmount());
					}
			System.out.println("0");
			if (this.mExplorer.isColorComplete(this.player,
					this.mLoader.getRegions().get(region).getCities().get(city).getColor(), this.allMapCities)) {
				if (!this.mLoader.getRegions().get(region).getCities().get(city).isCapital()) {
					BVictoryPoints playerBReward = bRewardManageCopy.getBoardColorReward(
							this.mLoader.getRegions().get(region).getCities().get(city).getColor());
					this.rewardAmount += playerBReward.getAmount();
					System.out.printf("Color %s achieved: ",
							this.mLoader.getRegions().get(region).getCities().get(city).getColor());
					System.out.println(playerBReward.getAmount());
				}
			}
			if (this.mLoader.getRegions().get(region).isCompleted(this.player)) {
				BVictoryPoints playerBReward = bRewardManageCopy
						.getBoardRegionReward(this.mLoader.getRegions().get(region));
				this.rewardAmount += playerBReward.getAmount();
				System.out.printf("Region %d achieved: ", region);
				System.out.println(playerBReward.getAmount());
			}
		} while ((!cityNumbers.isEmpty()) && (!this.player.getEmporium().isEmpty()));
		System.out.println();
		System.out
				.println("Total VPoints effectly awarded by the player: " + this.player.getVictoryPoints().getAmount());
		System.out.print("Total VPoints that should be awarded to the player: " + this.rewardAmount);
		assertEquals(this.player.getVictoryPoints().getAmount(), this.rewardAmount);
	}
}