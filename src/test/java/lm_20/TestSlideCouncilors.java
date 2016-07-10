package lm_20;

import static org.junit.Assert.*;
import java.awt.Color;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import server.model.action.ASlideCouncil;
import server.model.action.ASlideCouncilWithAssistant;
import server.model.action.IllegalActionException;
import server.model.board.council.Council;
import server.model.board.council.CouncilorPool;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.board.nobility.TrackXMLFileException;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.player.Player;

public class TestSlideCouncilors {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
	private Council council;

	/**
	 * Creates a list with the colors and it initializes the player
	 * @throws ConfigurationErrorException 
	 * @throws TrackXMLFileException 
	 */
	@Before
	public void setUp() throws ConfigurationErrorException, TrackXMLFileException {
		Configuration config= new Configuration();
		colorList = config.getColorsList();
		this.player= new Player(config, new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack()));
		player.getCoins().increaseAmount(10);
		player.getAssistants().increaseAmount(3);
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
		assertEquals(2, player.getAssistants().getAmount());
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
		colorsFromCouncil.get(0);
	}

	/**
	 * Tests the extra action when the money aren't enough
	 */
	@Test(expected = IllegalActionException.class)
	public void testSlideCouncilWithExtraFailedNoAssistants() throws Exception {

		player.getAssistants().decreaseAmount(3);
		ASlideCouncilWithAssistant action = new ASlideCouncilWithAssistant(player, pool, council, colorList.get(1));
		action.execute();
		List<Color> colorsFromCouncil = council.getCouncilorsColor();
		colorsFromCouncil.get(0);
	}
}
