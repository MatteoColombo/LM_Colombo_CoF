package lm_20;

import static org.junit.Assert.*;
import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

import game.board.CouncilorPool;
import game.board.Council;

public class TestCouncilorPool {

	@Test
	public void test() {
		ArrayList<Color> colorList = new ArrayList<Color>();
		colorList.add(new Color(20, 30, 40));
		colorList.add(new Color(100, 30, 50));
		colorList.add(new Color(200, 130, 140));
		colorList.add(new Color(2, 3, 40));
		colorList.add(new Color(2, 3, 4));
		colorList.add(new Color(255, 255, 255));
		CouncilorPool cp = new CouncilorPool(4, 4, colorList);
		
		assertEquals(true, cp.isFull(colorList.get(3)));
		assertEquals(true, cp.isFull(colorList.get(0)));
		assertEquals(true, cp.isFull(colorList.get(5)));
		
		assertEquals(true, cp.isAvailable(colorList.get(0)));
		assertEquals(true, cp.isAvailable(colorList.get(2)));
		assertEquals(true, cp.isAvailable(colorList.get(5)));
		
		try{ 
			Council council = cp.getCouncil();
			Color extractedColor = council.getCouncilor(2).getColor();
			assertEquals(false, cp.isFull(extractedColor));
		} catch(Exception e) {
			fail("a council should be available");
		}
		
	}

}
