package lm_20;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import server.model.action.ABuyAssistant;
import server.model.action.IllegalActionException;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.board.nobility.TrackXMLFileException;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.player.Player;

public class TestABuyAssistants {

	private Player p;
	private Configuration config;
	/**
	 * Creates a list with the colors and it initializes the player
	 * @throws ConfigurationErrorException 
	 * @throws TrackXMLFileException 
	 */
	@Before
	public void setUp() throws TrackXMLFileException, ConfigurationErrorException {
		config= new Configuration();
		p= new Player(config, new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack()));
		p.getCoins().increaseAmount(10);
		p.getAssistants().increaseAmount(1);
	}

	/**
	 * Buys an assistant when coins are enough
	 * 
	 * @throws IllegalActionException
	 */
	@Test
	public void testActionBuyAssistant() throws IllegalActionException {
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
		p.getCoins().decreaseAmount(8);
		assertEquals(2, p.getCoins().getAmount());
		ABuyAssistant action = new ABuyAssistant(this.p);
		action.execute();

	}

}
