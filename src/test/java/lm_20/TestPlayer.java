package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.player.Assistants;
import game.player.Coins;
import game.player.Emporium;
import game.player.NoblePoints;
import game.player.Player;
import game.player.VictoryPoints;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {

	private List<Color> colorList;
	private Player p;

	/**
	 * Creates a list with the colors and it initializes the player
	 */
	@Before
	public void setUp() {
		colorList = new ArrayList<Color>();
		colorList.add(new Color(20, 30, 40));
		colorList.add(new Color(100, 30, 50));
		colorList.add(new Color(200, 130, 140));
		colorList.add(new Color(2, 3, 40));
		colorList.add(new Color(2, 3, 4));
		colorList.add(new Color(255, 255, 255));

		p = new Player(10, 1, 6, 10, colorList, 0, 0);
	}

	@Test
	public void testCoins() {
		Coins c = p.getCoins();
		c.increaseAmount(5);
		c.decreaseAmount(3);
		assertEquals(12, c.getAmount());
	}

	@Test
	public void testAssistants() {
		Assistants a = p.getAssistants();
		a.increaseAmount(2);
		a.decreaseAmount(1);
		assertEquals(2, a.getAmount());
	}

	@Test
	public void testVictoryPoinrs() {

		VictoryPoints v = p.getVictoryPoints();
		v.increaseAmount(5);
		assertEquals(5, v.getAmount());

	}

	@Test
	public void testEmporium() {
		Emporium e = p.getEmporium().get(0);
		assertEquals(p, e.getPlayer());
		assertEquals(null, e.getCity());
	}

	@Test
	public void testNoblePoint() {
		NoblePoints n = p.getNoblePoints();
		n.increaseAmount(1);
		assertEquals(1, n.getAmount());
		n.increaseAmount(2);
		assertEquals(3, n.getAmount());
	}

	@Test
	public void testActions() {
		p.doExtraAction();
		assertEquals(true, p.getIfExtraActionDone());
		p.doMainAction();
		assertEquals(0, p.getMainActionsLeft());
		p.actionsReset();
		assertEquals(1, p.getMainActionsLeft());
		assertEquals(false, p.getIfExtraActionDone());
	}

}
