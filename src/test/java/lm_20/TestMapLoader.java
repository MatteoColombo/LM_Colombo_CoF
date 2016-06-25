package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.board.council.CouncilorPool;
import model.board.map.MapLoader;
import model.exceptions.MapXMLFileException;

public class TestMapLoader {

	private ArrayList<Color> colors;

	@Before
	public void setUp() {
		colors = new ArrayList<Color>();
		colors.add(Color.BLACK);
		colors.add(Color.WHITE);
		colors.add(Color.YELLOW);
		colors.add(Color.DARK_GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
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
		ml.getCitiesList();
	}
}
