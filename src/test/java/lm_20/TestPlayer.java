package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import org.junit.Before;
import org.junit.Test;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.TrackXMLFileException;
import server.model.player.Assistants;
import server.model.player.Coins;
import server.model.player.Emporium;
import server.model.player.Nobility;
import server.model.player.Player;
import server.model.player.PoliticCard;
import server.model.player.VictoryPoints;

public class TestPlayer {

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
		NobilityTrack track= new NobilityTrack(new NobilityLoader(new Configuration().getNobility()).getNobilityTrack());
		p= new Player(config, track);
		p.getCoins().increaseAmount(10);
		p.getAssistants().increaseAmount(1);
		p.getVictoryPoints().increaseAmount(1);
		
	}

	/**
	 * Test the coins 
	 */
	@Test
	public void testCoins() {
		Coins c = p.getCoins();
		c.increaseAmount(5);
		c.decreaseAmount(3);
		assertEquals(12, c.getAmount());
	}

	/**
	 * Test the assistants
	 */
	@Test
	public void testAssistants() {
		Assistants a = p.getAssistants();
		a.increaseAmount(2);
		a.decreaseAmount(1);
		assertEquals(2, a.getAmount());
		assertNotEquals(a, p);
	}

	/**
	 * Test the victory points
	 */
	@Test
	public void testVictoryPoinrs() {

		VictoryPoints v = p.getVictoryPoints();
		v.increaseAmount(5);
		assertEquals(5, v.getAmount());

	}

	/**
	 * Test the emporiums
	 */
	@Test
	public void testEmporium() {
		Emporium e = p.getEmporium().get(0);
		assertEquals(p, e.getPlayer());
	}

	/**
	 * Test the pointer to the noble's track
	 */
	@Test
	public void testNoblePoint() {
		Nobility n = p.getNobility();
 		assertEquals(0, n.getAmount());
		n.increaseAmount(3);
		assertEquals(3, n.getAmount());
	}

	/**
	 * Tests main and extra actions
	 */
	@Test
	public void testActions() {
		p.doExtraAction();
		assertEquals(true, p.getIfExtraActionDone());
		p.doMainAction();
		assertEquals(0, p.getMainActionsLeft());
		p.actionsReset();
		assertEquals(1, p.getMainActionsLeft());
		assertEquals(false, p.getIfExtraActionDone());
		p.doMainAction();
		p.increaseMainAction();
		assertEquals(1, p.getMainActionsLeft());
	}
	
	/**
	 * Tests the methods to draw a new cards, checks if it is effectively added
	 */
	@Test 
	public void testPoliticCards(){
		p.drawAPoliticCard();
		assertEquals(7, p.getPoliticCard().size());
		System.out.println(p.getPoliticCard().get(0).isMultipleColor());
		System.out.println(p.getPoliticCard().get(0).getMarketMessage(config));
		assertNotEquals(p.getPoliticCard().get(0), p);
		PoliticCard c= new PoliticCard((Color)null);
		assertEquals(true, c.isMultipleColor());
	}
	
	/**
	 * Test the equals methods
	 */
	@Test
	public void testEquals(){
		Assistants ass2= new Assistants(p.getAssistants().getAmount());
		assertEquals(p.getAssistants(), ass2);
		ass2.increaseAmount(2);
		assertNotEquals(p.getAssistants(), ass2);
		PoliticCard pol2= new PoliticCard(p.getPoliticCard().get(0).getCardColor());
		assertEquals(p.getPoliticCard().get(0), pol2);
		pol2= new PoliticCard(Color.decode("#eeeeee"));
		assertNotEquals(p.getPoliticCard().get(0), pol2);
		assertEquals(null, p.getClient());
		
	}
	
	@Test
	public void testClone(){
		p.setSuspension(true);
		Player p2= p.getClientCopy();
		assertNotEquals(p.getSuspended(), p2.getSuspended());
	}
	
	@Test
	public void testGamePlayer() throws TrackXMLFileException{
		Player gamePlayer= new Player(config,1,null,new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack()));
		assertEquals(11, gamePlayer.getCoins().getAmount());
		assertEquals(2, gamePlayer.getAssistants().getAmount());
	}
}
