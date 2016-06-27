package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import server.model.board.council.Council;
import server.model.board.council.CouncilorPool;

public class TestCouncilorPool {
	ArrayList<Color> colorList = new ArrayList<Color>();
	CouncilorPool cp;

	@Before
	public void setUp() throws Exception {
		colorList.add(Color.WHITE);
		colorList.add(Color.BLACK);
		colorList.add(Color.RED);
		colorList.add(Color.BLUE);
	}
	
	@Test
	public void testQueries() {
		cp = new CouncilorPool(4, 4, colorList);
		assertEquals(true, cp.isFull(colorList.get(0)));
		assertEquals(true, cp.isFull(colorList.get(1)));
		assertEquals(true, cp.isFull(colorList.get(2)));
		assertEquals(true, cp.isAvailable(colorList.get(0)));
		assertEquals(true, cp.isAvailable(colorList.get(1)));
		assertEquals(true, cp.isAvailable(colorList.get(2)));
	}
	
	@Test
	public void testSlide() {
		cp = new CouncilorPool(4, 4, colorList);
		Color requested = colorList.get(1);
		Council council=cp.getCouncil();
		cp.slideCouncilor(council, requested);
		assertEquals(council.getCouncilorsColor().get(3), requested);
		assertEquals(false, cp.isFull(council.getHeadColor()));
		assertEquals(true, cp.isAvailable(colorList.get(0)));
		assertEquals(true, cp.isAvailable(colorList.get(1)));
		assertEquals(true, cp.isAvailable(colorList.get(2)));
	}
	
	@Test
	public void testAvailability() {
		cp = new CouncilorPool(4, 4, colorList);
		assertEquals(true, cp.canGenerateCouncil());
		cp.getCouncil();
		cp.getCouncil();
		cp.getCouncil();
		cp.getCouncil();
		assertEquals(false, cp.canGenerateCouncil());
	}
}
