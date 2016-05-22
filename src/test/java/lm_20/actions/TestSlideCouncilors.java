package lm_20.actions;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.action.ASlideCouncil;
import game.board.council.Council;
import game.board.council.CouncilorPool;
import game.exceptions.IllegalActionException;
import game.player.Player;

public class TestSlideCouncilors {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
	private Council council;

	/**
	 * Creates a list with the colors and it initializes the player
	 */
	@Before
	public void setUp() {
		colorList = new ArrayList<Color>();
		colorList.add(new Color(20, 30, 40));
		colorList.add(new Color(100, 30, 50));
		colorList.add(new Color(200, 130, 140));
		colorList.add(new Color(2, 3, 40));
		colorList.add(new Color(2, 3, 4));
		colorList.add(new Color(255, 255, 255));
		this.player = new Player(10, 1, 6, 10, coldorList, 0, 0);
		this.pool= new CouncilorPool(4, 4, colorList);
		this.council= pool.getCouncil();
	}

	@Test
	public void testSlideCouncil(){
		try{
			Color c= council.getHeadColor();
			pool.slideCouncilor(council, colorList.get(1));
			pool.slideCouncilor(council, colorList.get(2));
			pool.slideCouncilor(council, colorList.get(3));
			ASlideCouncil action= new ASlideCouncil(player, pool, council, colorList.get(4));
			action.execute();
			List<Color> colorsFromCouncil= council.getCouncilorsColor();
			assertEquals(colorList.get(1), council.getHeadColor());
			assertEquals(colorList.get(1), colorsFromCouncil.get(0));
			assertEquals(colorList.get(2), colorsFromCouncil.get(1));
			assertEquals(colorList.get(3), colorsFromCouncil.get(2));
			assertEquals(colorList.get(4), colorsFromCouncil.get(3));
			assertEquals(14, player.getCoins().getAmount());
		}catch(IllegalActionException iae){
			
		}
	}
}
