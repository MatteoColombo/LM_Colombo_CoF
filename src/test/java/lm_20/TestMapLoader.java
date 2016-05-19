package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

import game.board.CouncilorPool;
import game.board.MapLoader;

public class TestMapLoader {

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
		MapLoader ml= new MapLoader("src/main/resources/map.xml", new CouncilorPool(4, 4, colors));
		assertEquals(ml.getRegionsNumber(),3);
		ml.getRegions();
		ml.getKingCity();
		}catch(Exception e){
			
		}
	}

}
