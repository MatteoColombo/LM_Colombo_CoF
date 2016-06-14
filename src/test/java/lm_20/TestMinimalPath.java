package lm_20;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import java.util.ArrayList;

import org.junit.Test;

import model.board.city.City;
import model.board.council.CouncilorPool;
import model.board.map.MapExplorer;
import model.board.map.MapLoader;

public class TestMinimalPath {

	
	
	@Test
	public void test() throws Exception{
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.ALICEBLUE);
		colors.add(Color.ANTIQUEWHITE);
		colors.add(Color.AQUA);
		colors.add(Color.AQUAMARINE);
		colors.add(Color.AZURE);
		colors.add(Color.BEIGE);
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
