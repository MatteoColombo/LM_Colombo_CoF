package lm_20;

import static org.junit.Assert.*;
import org.junit.Test;

import game.board.Councilor;

import java.awt.Color;
public class TestCouncilor {

	@Test
	public void test() {
		Color c = new Color(200, 10, 10);
		Councilor counc = new Councilor(c);
		assertEquals(counc.getColor(), c);
	}
	
}
