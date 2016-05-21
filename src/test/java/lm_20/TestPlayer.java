package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import game.player.Assistants;
import game.player.Coins;
import game.player.Emporium;
import game.player.NoblePoints;
import game.player.Player;
import game.player.VictoryPoints;

import org.junit.Test;

public class TestPlayer {

	@Test
	public void test() {
		ArrayList<Color> colorList = new ArrayList<Color>();
		colorList.add(new Color(20, 30, 40));
		colorList.add(new Color(100, 30, 50));
		colorList.add(new Color(200, 130, 140));
		colorList.add(new Color(2, 3, 40));
		colorList.add(new Color(2, 3, 4));
		colorList.add(new Color(255, 255, 255));
		
		
		Player p= new Player(10,1,6,10, colorList,0,0);
		
		Coins c= p.getCoins();
		c.increment(5);
		c.decrease(3);
		assertEquals(12, c.getAmount());
		
		Assistants a= p.getAssistants();
		a.increment(2);
		a.decrease(1);
		assertEquals(2, a.getAmount());
		
		VictoryPoints v = p.getVictoryPoints();
		v.increment(5);
		assertEquals(5, v.getAmount());
		
		Emporium e= p.getEmporium().get(0);
		assertEquals(p, e.getPlayer());
		assertEquals(null, e.getCity());
		
		NoblePoints n = p.getNoblePoints();
		n.increment(1);
		assertEquals(1,n.getAmount());
		
		p.doExtraAction();
		assertEquals(true, p.getIfExtraActionDone());
		p.doMainAction();
		assertEquals(0, p.getMainActionsLeft());
		p.actionsReset();
		assertEquals(1, p.getMainActionsLeft());
		assertEquals(false, p.getIfExtraActionDone());
	}

}
