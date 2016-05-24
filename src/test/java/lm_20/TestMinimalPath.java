package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

import game.board.city.City;
import game.board.council.CouncilorPool;
import game.board.map.MapExplorer;
import game.board.map.MapLoader;

public class TestMinimalPath {

	
	
	@Test
	public void test() {
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(new Color(20, 30, 40));
		colors.add(new Color(100, 30, 50));
		colors.add(new Color(200, 130, 140));
		colors.add(new Color(2, 3, 40));
		colors.add(new Color(2, 3, 4));
		colors.add(new Color(255, 255, 255));
		try{
			MapLoader ml = new MapLoader("src/main/resources/map.xml", new CouncilorPool(4, 4, colors));
			City first=ml.getRegions().get(1).getCities().get(1);
			System.out.println(first.getName());
			City second= ml.getRegions().get(0).getCities().get(4);
			System.out.println(second.getName());
			MapExplorer mx= new MapExplorer();
			System.out.println(mx.getDistance(first, second));
			assertEquals(3,mx.getDistance(first, second));
			assertEquals(0,mx.getDistance(second, second));
			mx.getDistance(first, new City(Color.YELLOW, "Milan"));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
