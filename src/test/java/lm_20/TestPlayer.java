package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import game.board.exceptions.NegativeException;
import game.player.Player;
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
		assertEquals(p.getAssistants().getAmount(), 1);
		assertEquals(p.getPoliticCard().size(), 6);
		
		p.getAssistants().increment(1);
		
		p.getCoins().getAmount();
		p.getCoins().decrease(3);
		p.getCoins().increment(4);
		p.drawAPoliticCard();
		
		p.getVictoryPoints();
		
		p.getNoblePoints();
		
		p.getPermissionCard();
		
		p.getEmporium();
	
		p.getMainActionsLeft();
		
		assertEquals(p.getAssistants().getAmount(), 2);
		assertEquals(p.getCoins().getAmount(),11);
		assertEquals(p.getPoliticCard().size(), 7);
		
		
	}

}
