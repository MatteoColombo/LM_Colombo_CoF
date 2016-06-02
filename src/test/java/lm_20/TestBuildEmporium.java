package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.action.ABuildEmporium;
import model.action.ABuildEmporiumWithKing;
import model.board.BoardRewardsManager;
import model.board.King;
import model.board.council.CouncilorPool;
import model.board.map.MapLoader;
import model.exceptions.IllegalActionException;
import model.exceptions.MapXMLFileException;
import model.player.PermissionCard;
import model.player.Player;
import model.reward.BVictoryPoints;
import model.reward.BoardColorReward;
import model.reward.BoardRegionReward;

public class TestBuildEmporium {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
/*	private List<BoardColorReward> bColorRewards;
	private List<BoardRegionReward> bRegionRewards;
	private List<BVictoryPoints> bKingRewards;
	private BoardRewardsManager bRewardManager;*/

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
		/*bColorRewards = new ArrayList<>();
		bKingRewards = new ArrayList<>();
		bRegionRewards = new ArrayList<>();
		bColorRewards.add(new BoardColorReward(Color.decode("#008000"), 10));
		bColorRewards.add(new BoardColorReward(Color.decode("#2268df"), 1));
		bColorRewards.add(new BoardColorReward(Color.decode("#ffd700"), 50));
		bColorRewards.add(new BoardColorReward(Color.decode("#f44343"), 20));
		MapLoader ml = new MapLoader("src/main/resources/map.xml", pool);
		bRegionRewards.add(new BoardRegionReward(ml.getRegions().get(0), 20));
		this.bRewardManager = new BoardRewardsManager(bColorRewards, bRegionRewards, bKingRewards);*/

	}

	/**
	 * Builds an emporium using a permit card, the city hasn't other emporiums
	 * after the first emporium it tries to build a second one in the same city
	 * with the same player, the action is rejected
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithPermission() throws Exception {
		MapLoader ml;
		PermissionCard card;
		ABuildEmporium action;
		ml = new MapLoader("src/main/resources/map.xml", pool);
		card = new PermissionCard(ml.getRegions().get(0).getCities());
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), null, null);
		assertEquals(true, action.isMain());
		action.execute();
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), null, null);
	}

	/**
	 * Tries to build an emporium in a city which doesn't satisfies the permit
	 * card
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithWrongCity() throws Exception {
		MapLoader ml;
		PermissionCard card;
		ABuildEmporium action;
		ml = new MapLoader("src/main/resources/map.xml", pool);
		card = new PermissionCard(ml.getRegions().get(0).getCities());
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), null, null);
		action.execute();
		action = new ABuildEmporium(player, card, ml.getRegions().get(2).getCities().get(0), null, null);
	}

	/**
	 * Tries to build an emporium in a city which already has another emporium
	 * of another player. The player has no money and the action is rejected
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithAnotherPlayerEmporium() throws Exception {
		MapLoader ml;
		PermissionCard card;
		ABuildEmporium action;
		ml = new MapLoader("src/main/resources/map.xml", pool);
		card = new PermissionCard(ml.getRegions().get(0).getCities());
		Player p2 = new Player(10, 0, 6, 10, colorList, 0, 0);
		action = new ABuildEmporium(player, card, card.getCardCity().get(0), null, null);
		action.execute();
		action = new ABuildEmporium(p2, card, card.getCardCity().get(0), null, null);
		action.execute();
	}

	/**
	 * Builds an emporium using the king in a different city and then tries to
	 * build another in the same
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithKing() throws Exception {
		MapLoader ml;
		King king;
		ABuildEmporiumWithKing action;

		ml = new MapLoader("src/main/resources/map.xml", pool);
		king = new King(ml.getKingCity(), pool.getCouncil());
		action = new ABuildEmporiumWithKing(player, king, ml.getRegions().get(0).getCities().get(0), null, null);
		assertEquals(true, action.isMain());
		action.execute();
		assertEquals(1, ml.getRegions().get(0).getCities().get(0).getNumberOfEmporium());
		action = new ABuildEmporiumWithKing(player, king, ml.getRegions().get(0).getCities().get(0), null, null);

	}

	/**
	 * Builds an emporium using the king in a differente city but can't afford
	 * it
	 */
	@Test(expected = IllegalActionException.class)
	public void testBuildWithKingNoMoney() throws Exception {
		MapLoader ml;
		King king;
		ABuildEmporiumWithKing action;
		this.player.getCoins().decreaseAmount(3);

		ml = new MapLoader("src/main/resources/map.xml", pool);
		king = new King(ml.getKingCity(), pool.getCouncil());
		action = new ABuildEmporiumWithKing(player, king, ml.getRegions().get(0).getCities().get(0), null, null);
		assertEquals(true, action.isMain());
		action.execute();
		assertEquals(10, player.getCoins().getAmount());
	}

}
