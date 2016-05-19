package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

import game.board.Council;
import game.board.Councilor;

public class TestCouncil {

	@Test
	public void testStandard() {	
		ArrayList<Color> colorList = new ArrayList<Color>();
		colorList.add(new Color(20, 30, 40));
		colorList.add(new Color(100, 30, 50));
		colorList.add(new Color(200, 130, 140));
		
		ArrayList<Councilor> councList = new ArrayList<>();
		councList.add(new Councilor(colorList.get(0)));
		councList.add(new Councilor(colorList.get(1)));
		councList.add(new Councilor(colorList.get(2)));
		
		Council council = new Council(councList);
		
		assertEquals(council.getCouncilorsColor(), colorList);

		Councilor counc4 = new Councilor(new Color(100, 100, 100));
		
		council.insertCouncilor(counc4);
		
		assertEquals(counc4.getColor(), council.getCouncilorsColor().get(3));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testEmpty() {
		ArrayList<Councilor> councList = new ArrayList<>();
		Council council = new Council(councList);
		
		Councilor counc = new Councilor(new Color(100, 100, 100));		
		council.insertCouncilor(counc);		
	}
	
}
