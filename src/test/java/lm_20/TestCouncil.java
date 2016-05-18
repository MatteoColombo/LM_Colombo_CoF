package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

import game.board.Council;
import game.board.Councilor;

public class TestCouncil {

	@Test
	public void test() {
		testStandard();
		testEmpty();
	}

	private void testStandard() {	
		Color c1 = new Color(200, 10, 10);
		Councilor counc1 = new Councilor(c1);
		Color c2 = new Color(20, 0, 250);
		Councilor counc2 = new Councilor(c2);
		Color c3 = new Color(0, 10, 255);
		Councilor counc3 = new Councilor(c3);
		
		ArrayList<Councilor> councList = new ArrayList<>();
		councList.add(counc1);
		councList.add(counc2);
		councList.add(counc3);
		
		Council council = new Council(councList);
		
		assertEquals(council.getCouncilor(0), counc1);
		assertEquals(council.getCouncilor(1), counc2);
		assertEquals(council.getCouncilor(2), counc3);

		Councilor counc4 = new Councilor(new Color(100, 100, 100));
		
		council.insertCounciler(counc4);
		
		assertEquals(council.getCouncilor(0), counc2);
		assertEquals(council.getCouncilor(1), counc3);
		assertEquals(council.getCouncilor(2), counc4);
	}
	
	private void testEmpty() {
		ArrayList<Councilor> councList = new ArrayList<>();
		Council council = new Council(councList);
		
		Councilor counc = new Councilor(new Color(100, 100, 100));		
		council.insertCounciler(counc);		
	}
	
}
