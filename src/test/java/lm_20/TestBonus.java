package lm_20;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.player.Player;
import model.reward.BAssistants;
import model.reward.Bonus;

/**
 * 
 * @author Davide Cavallini
 *
 */
public class TestBonus {
	private List<Color> colorList;
	private Player p;

	@Before
	public void initialization() {
		colorList = new ArrayList<Color>();
		colorList.add(Color.ALICEBLUE);
		colorList.add(Color.ANTIQUEWHITE);
		colorList.add(Color.AQUA);
		colorList.add(Color.AQUAMARINE);
		colorList.add(Color.AZURE);
		colorList.add(Color.BEIGE);

		p = new Player(10, 1, 6, 10, colorList, 0, 0);
	}

	@Test
	public void testBAssistantsConstructor() {
		BAssistants ba = new BAssistants(10);
		assertEquals(10, ba.getAmount());
	}
	@Test
	public void testBAssistantsMethod_newCopy() {
		BAssistants ba1 = new BAssistants(10);
		Bonus ba2 = ba1.newCopy(10);		
		assertEquals(ba2.getAmount(), ba1.getAmount());
	}
	@Test
	public void testBAssistantsMethod_getValue() {
		BAssistants ba = new BAssistants(10);	
		assertEquals(33, ba.getValue());
	}
	@Test
	public void testBAssistantsMethod_assignBonusTo() {
		BAssistants ba = new BAssistants(10);
		ba.assignBonusTo(p);		
		assertEquals(11, p.getAssistants().getAmount());
	}
}