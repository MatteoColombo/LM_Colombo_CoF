package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.board.council.Council;
import game.board.council.Councilor;
import game.board.council.CouncilorPool;

public class TestCouncil {
	ArrayList<Color> colorList = new ArrayList<Color>();
	ArrayList<Councilor> councList = new ArrayList<>();
	Council council;
	Councilor councilor;
	Color color;
	CouncilorPool cp;

	
	@Before
	public void setUp() throws Exception {
		colorList.add(new Color(20, 30, 40));
		colorList.add(new Color(100, 30, 50));
		colorList.add(new Color(200, 130, 140));
		councList.add(new Councilor(colorList.get(0)));
		councList.add(new Councilor(colorList.get(1)));
		councList.add(new Councilor(colorList.get(2)));
		council = new Council(councList);
		
		color = new Color(100, 100, 100);
		councilor = new Councilor(color);
		cp = new CouncilorPool(4, 4, colorList);
	}
	
	@Test
	public void testCouncilor() {
		assertEquals(councilor.getColor(), color);
	}
	
	@Test
	public void testCouncil() {				
		assertEquals(council.getCouncilorsColor(), colorList);
	
		council.insertCouncilor(councilor);		
		assertEquals(councilor.getColor(), color);
	}
	
	@Test
	public void TestCouncilorPool() {
		assertEquals(true, cp.isFull(colorList.get(0)));
		assertEquals(true, cp.isFull(colorList.get(1)));
		assertEquals(true, cp.isFull(colorList.get(2)));
		
		assertEquals(true, cp.isAvailable(colorList.get(0)));
		assertEquals(true, cp.isAvailable(colorList.get(1)));
		assertEquals(true, cp.isAvailable(colorList.get(2)));
		
		Color requested = colorList.get(1);

		cp.slideCouncilor(council, colorList.get(1));
		assertEquals(council.getCouncilorsColor().get(2), requested);
		
		council=cp.getCouncil();
		assertEquals(false, cp.isFull(council.getHeadColor()));

		
		
		
	}
}
