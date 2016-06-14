package lm_20;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.player.Assistants;
import model.player.Coins;
import model.player.Emporium;
import model.player.NoblePoints;
import model.player.Player;
import model.player.PoliticCard;
import model.player.VictoryPoints;

public class TestPlayer {

	private List<Color> colorList;
	private Player p;

	/**
	 * Creates a list with the colors and it initializes the player
	 */
	@Before
	public void setUp() {
		colorList = new ArrayList<Color>();
		colorList.add(Color.ALICEBLUE);
		colorList.add(Color.ANTIQUEWHITE);
		colorList.add(Color.AQUA);
		colorList.add(Color.AQUAMARINE);
		colorList.add(Color.AZURE);
		colorList.add(Color.BEIGE);

		p = new Player(10, 1, 6, 10, colorList, 0, 0);
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
		NoblePoints n = p.getNoblePoints();
		n.increaseAmount(1);
		assertEquals(1, n.getAmount());
		n.increaseAmount(2);
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
	}

}
