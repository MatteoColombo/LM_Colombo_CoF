package lm_20;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
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
		colors.add(Color.ALICEBLUE);
		colors.add(Color.ANTIQUEWHITE);
		colors.add(Color.AQUA);
		colors.add(Color.AQUAMARINE);
		colors.add(Color.AZURE);
		colors.add(Color.BEIGE);
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
