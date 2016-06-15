package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import model.action.ABuyAssistant;
import model.exceptions.IllegalActionException;
import model.player.Player;

public class TestABuyAssistants {

	private List<Color> colorList;
	private Player p;

	/**
	 * Creates a list with the colors and it initializes the player
	 */
	@Before
	public void setUp() {
		colorList = new ArrayList<Color>();
		colorList.add(Color.BLACK);
		colorList.add(Color.WHITE);
		colorList.add(Color.YELLOW);
		colorList.add(Color.DARK_GRAY);
		colorList.add(Color.GREEN);
		colorList.add(Color.BLUE);

		p = new Player(10, 1, 6, 10, colorList, 0, 0,null);
	}

	/**
	 * Buys an assistant when coins are enough
	 * 
	 * @throws IllegalActionException
	 */
	@Test
	public void testActionBuyAssistant() throws IllegalActionException {
		p = new Player(10, 1, 6, 10, colorList, 0, 0);
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
		p = new Player(10, 1, 6, 10, colorList, 0, 0);
		p.getCoins().decreaseAmount(8);
		assertEquals(2, p.getCoins().getAmount());
		ABuyAssistant action = new ABuyAssistant(this.p);
		action.execute();

	}

}
