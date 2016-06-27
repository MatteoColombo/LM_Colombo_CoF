package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

import server.model.board.city.City;
import server.model.board.council.CouncilorPool;
import server.model.board.map.MapExplorer;
import server.model.board.map.MapLoader;

public class TestMinimalPath {

	
	
	@Test
	public void test() throws Exception{
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.BLACK);
		colors.add(Color.WHITE);
		colors.add(Color.YELLOW);
		colors.add(Color.DARK_GRAY);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
			MapLoader ml = new MapLoader("src/main/resources/map.xml", new CouncilorPool(4, 4, colors));
			City first=ml.getRegions().get(1).getCities().get(1);
			System.out.println(first.getName());
			City second= ml.getRegions().get(0).getCities().get(4);
			System.out.println(second.getName());
			MapExplorer mx= new MapExplorer();
			System.out.println(mx.getDistance(first, second));
			assertEquals(3,mx.getDistance(first, second));
			//assertEquals(0,mx.getDistance(second, second));
			//mx.getDistance(first, new City(Color.YELLOW, "Milan"));
	}

}
