package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.model.action.ABuyAssistant;
import server.model.action.IllegalActionException;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.TrackXMLFileException;
import server.model.player.Player;

public class TestABuyAssistants {

	private List<Color> colorList;
	private Player p;
	private NobilityTrack track;
	/**
	 * Creates a list with the colors and it initializes the player
	 * @throws ConfigurationErrorException 
	 * @throws TrackXMLFileException 
	 */
	@Before
	public void setUp() throws TrackXMLFileException, ConfigurationErrorException {
		colorList = new ArrayList<Color>();
		colorList.add(Color.BLACK);
		colorList.add(Color.WHITE);
		colorList.add(Color.YELLOW);
		colorList.add(Color.DARK_GRAY);
		colorList.add(Color.GREEN);
		colorList.add(Color.BLUE);
		track= new NobilityTrack(new NobilityLoader(new Configuration().getNobility()).getNobilityTrack());
		
		p = new Player(10, 1, 6, 10, colorList, 0, 0,track,null);
	}

	/**
	 * Buys an assistant when coins are enough
	 * 
	 * @throws IllegalActionException
	 */
	@Test
	public void testActionBuyAssistant() throws IllegalActionException {
		p = new Player(10, 1, 6, 10, colorList, 0, 0,track,null);
		ABuyAssistant action = new ABuyAssistant(this.p);
		action.execute();
		assertEquals(7, p.getCoins().getAmount());
		assertEquals(2, p.getAssistants().getAmount());
	}

	/**
	 * Tests when coins aren't enough to buy a new assistant
	 */
	@Test(expected = IllegalActionException.class)
	public void testActionBuyAssistantFailed() throws Exception {
		p = new Player(10, 1, 6, 10, colorList, 0, 0,track,null);
		p.getCoins().decreaseAmount(8);
		assertEquals(2, p.getCoins().getAmount());
		ABuyAssistant action = new ABuyAssistant(this.p);
		action.execute();

	}

}
