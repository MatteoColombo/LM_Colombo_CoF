package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.action.ASlideCouncil;
import game.action.ASlideCouncilWithAssistant;
import game.board.council.Council;
import game.board.council.CouncilorPool;
import game.exceptions.IllegalActionException;
import game.player.Player;

public class TestSlideCouncilors {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
	private Council council;

	/**
	 * Creates a list with the colors and it initializes the player
	 */
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
		this.council = pool.getCouncil();
	}

	/**
	 * Insert 3 councilors in the council, then it adds another one with the
	 * action then test if the councilors are the expected and in the correct
	 * order
	 */
	@Test
	public void testSlideCouncil() throws Exception {
		pool.slideCouncilor(council, colorList.get(1));
		pool.slideCouncilor(council, colorList.get(2));
		pool.slideCouncilor(council, colorList.get(3));
		ASlideCouncil action = new ASlideCouncil(player, pool, council, colorList.get(4));
		action.execute();
		List<Color> colorsFromCouncil = council.getCouncilorsColor();
		assertEquals(colorList.get(1), council.getHeadColor());
		assertEquals(colorList.get(1), colorsFromCouncil.get(0));
		assertEquals(colorList.get(2), colorsFromCouncil.get(1));
		assertEquals(colorList.get(3), colorsFromCouncil.get(2));
		assertEquals(colorList.get(4), colorsFromCouncil.get(3));
		assertEquals(14, player.getCoins().getAmount());
		assertEquals(player.getMainActionsLeft(), 0);
		assertEquals(player.getIfExtraActionDone(), false);

	}

	/**
	 * Tests the extra action when the councilor is not available
	 */
	@Test(expected = IllegalActionException.class)
	public void testSlideCouncilFailedNoCouncilors() throws Exception {
		pool.slideCouncilor(council, colorList.get(1));
		pool.slideCouncilor(council, colorList.get(1));
		pool.slideCouncilor(council, colorList.get(1));
		pool.slideCouncilor(council, colorList.get(1));
		ASlideCouncil action = new ASlideCouncil(player, pool, council, colorList.get(1));
		action.execute();
	}

	/**
	 * Insert 3 councilors in the council, then it adds another one with the
	 * extra action then test if the councilors are the expected and in the
	 * correct orderù also checks if the assistant are decreased
	 */
	@Test
	public void testSlideCouncilWithExtra() throws Exception {
		pool.slideCouncilor(council, colorList.get(1));
		pool.slideCouncilor(council, colorList.get(2));
		pool.slideCouncilor(council, colorList.get(3));
		ASlideCouncilWithAssistant action = new ASlideCouncilWithAssistant(player, pool, council, colorList.get(4));
		action.execute();
		List<Color> colorsFromCouncil = council.getCouncilorsColor();
		assertEquals(colorList.get(1), council.getHeadColor());
		assertEquals(colorList.get(1), colorsFromCouncil.get(0));
		assertEquals(colorList.get(2), colorsFromCouncil.get(1));
		assertEquals(colorList.get(3), colorsFromCouncil.get(2));
		assertEquals(colorList.get(4), colorsFromCouncil.get(3));
		assertEquals(10, player.getCoins().getAmount());
		assertEquals(0, player.getAssistants().getAmount());
	}

	/**
	 * Tests the extra action when the councilor is not available
	 */
	@Test(expected = IllegalActionException.class)
	public void testSlideCouncilWithExtraFailedNoCouncilors() throws Exception {
		pool.slideCouncilor(council, colorList.get(1));
		pool.slideCouncilor(council, colorList.get(1));
		pool.slideCouncilor(council, colorList.get(1));
		pool.slideCouncilor(council, colorList.get(1));
		ASlideCouncilWithAssistant action = new ASlideCouncilWithAssistant(player, pool, council, colorList.get(1));
		action.execute();
		List<Color> colorsFromCouncil = council.getCouncilorsColor();

	}

	/**
	 * Tests the extra action when the money aren't enough
	 */
	@Test(expected = IllegalActionException.class)
	public void testSlideCouncilWithExtraFailedNoAssistants() throws Exception {

		player.getAssistants().decreaseAmount(2);
		ASlideCouncilWithAssistant action = new ASlideCouncilWithAssistant(player, pool, council, colorList.get(1));
		action.execute();
		List<Color> colorsFromCouncil = council.getCouncilorsColor();

	}
}
