package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.action.ABuildEmporium;
import game.action.ABuildEmporiumWithKing;
import game.board.King;
import game.board.council.CouncilorPool;
import game.board.map.MapLoader;
import game.exceptions.IllegalActionException;
import game.player.PermissionCard;
import game.player.Player;

public class TestBuildEmporium {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;

	@Before
	public void setUp() {
		colorList = new ArrayList<Color>();
		colorList.add(Color.BLACK);
		colorList.add(Color.BLUE);
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.ORANGE);
		this.player = new Player(10, 3, 6, 10, colorList, 0, 0);
		this.pool = new CouncilorPool(4, 4, colorList);
	}

	/**
	 * Builds an emporium using a permit card, the city hasn't other emporiums
	 * after the first emporium it tries to build a second one in the same city
	 * with the same player, the action is rejected
	 */
	@Test
	public void testBuildWithPermission() {
		MapLoader ml;
		PermissionCard card;
		ABuildEmporium action;
		try {
			ml = new MapLoader("src/main/resources/map.xml", pool);
			card = new PermissionCard(ml.getRegions().get(0).getCities());
			action = new ABuildEmporium(player, card, card.getCardCity().get(0));
			assertEquals(true, action.isMain());
			action.execute();
			action = new ABuildEmporium(player, card, card.getCardCity().get(0));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Tries to build an emporium in a city which doesn't satisfies the permit
	 * card
	 */
	@Test
	public void testBuildWithWrongCity() {
		MapLoader ml;
		PermissionCard card;
		ABuildEmporium action;
		try {
			ml = new MapLoader("src/main/resources/map.xml", pool);
			card = new PermissionCard(ml.getRegions().get(0).getCities());
			action = new ABuildEmporium(player, card, card.getCardCity().get(0));
			action.execute();
			action = new ABuildEmporium(player, card, ml.getRegions().get(2).getCities().get(0));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Tries to build an emporium in a city which already has another emporium
	 * of another player. The player has no money and the action is rejected
	 */
	@Test
	public void testBuildWithAnotherPlayerEmporium() {
		MapLoader ml;
		PermissionCard card;
		ABuildEmporium action;
		try {
			ml = new MapLoader("src/main/resources/map.xml", pool);
			card = new PermissionCard(ml.getRegions().get(0).getCities());
			Player p2 = new Player(10, 0, 6, 10, colorList, 0, 0);
			action = new ABuildEmporium(player, card, card.getCardCity().get(0));
			action.execute();
			action = new ABuildEmporium(p2, card, card.getCardCity().get(0));
			action.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Builds an emporium using the king in a different city and then tries to
	 * build another in the same
	 */
	@Test(expected=IllegalActionException.class)
	public void testBuildWithKing() throws Exception {
		MapLoader ml;
		King king;
		ABuildEmporiumWithKing action;

			ml = new MapLoader("src/main/resources/map.xml", pool);
			king = new King(ml.getKingCity(), pool.getCouncil());
			action = new ABuildEmporiumWithKing(player, king, ml.getRegions().get(0).getCities().get(0));
			assertEquals(true, action.isMain());
			action.execute();
			assertEquals(1, ml.getRegions().get(0).getCities().get(0).getNumberOfEmporium() );
			action = new ABuildEmporiumWithKing(player, king, ml.getRegions().get(0).getCities().get(0));
		
	}

	/**
	 * Builds an emporium using the king in a differente city but can't afford
	 * it
	 */
	@Test(expected=IllegalActionException.class)
	public void testBuildWithKingNoMoney() throws Exception {
		MapLoader ml;
		King king;
		ABuildEmporiumWithKing action;
		this.player.getCoins().decreaseAmount(3);
		
			ml = new MapLoader("src/main/resources/map.xml", pool);
			king = new King(ml.getKingCity(), pool.getCouncil());
			action = new ABuildEmporiumWithKing(player, king, ml.getRegions().get(0).getCities().get(0));
			assertEquals(true, action.isMain());
			action.execute();
			assertEquals(10, player.getCoins().getAmount());
	}

}
