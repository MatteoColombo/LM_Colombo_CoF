package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.action.ABuildEmporium;
import game.board.MapLoader;
import game.board.council.CouncilorPool;
import game.player.PermissionCard;
import game.player.Player;

public class TestBuildEmporium {
	private List<Color> colorList;
	private Player player;
	private CouncilorPool pool;
	
	@Before
	public void setUp() {
		colorList = new ArrayList<Color>();
		colorList.add(Color.BLACK);
		colorList.add(Color.BLUE);
		colorList.add(Color.RED);
		colorList.add(Color.YELLOW);
		colorList.add(Color.GREEN);
		colorList.add(Color.ORANGE);
		this.player = new Player(10, 3, 6, 10, colorList, 0, 0);
		this.pool= new CouncilorPool(4, 4, colorList);
	}
	
	@Test
	public void testBuildWithPermission() {
		try{
		MapLoader ml= new MapLoader("src/main/resources/map.xml", pool);
		PermissionCard card= new PermissionCard(ml.getRegions().get(0).getCities());
		ABuildEmporium action= new ABuildEmporium(player, card, card.getCardCity().get(0));
		action.execute();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
