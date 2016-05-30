package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.board.council.CouncilorPool;
import game.board.map.MapLoader;
import game.exceptions.MapXMLFileException;
import game.exceptions.XMLFileException;

public class TestMapLoader {

	private ArrayList<Color> colors;

	@Before
	public void setUp() {
		colors = new ArrayList<Color>();
		colors.add(new Color(20, 30, 40));
		colors.add(new Color(100, 30, 50));
		colors.add(new Color(200, 130, 140));
		colors.add(new Color(2, 3, 40));
		colors.add(new Color(2, 3, 4));
		colors.add(new Color(255, 255, 255));
	}

	
	@Test
	public void test() throws Exception{
		MapLoader ml= new MapLoader("src/main/resources/map.xml", new CouncilorPool(4, 4, colors) );
		assertEquals(ml.getRegionsNumber(),3);
		ml.getRegions();
		ml.getKingCity();
		
	}

	@Test(expected=MapXMLFileException.class)
	public void testExceptionMaokXML() throws Exception{
		MapLoader ml = new MapLoader("src/file.xml", new CouncilorPool(4, 4, colors));
	}
}
