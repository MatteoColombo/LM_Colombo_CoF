package lm_20;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import model.action.AShufflePermissionCards;
import model.action.Action;
import model.board.ColorConstants;
import model.board.Region;
import model.board.city.City;
import model.exceptions.IllegalActionException;
import model.player.PermissionCard;
import model.player.Player;
import model.reward.RewardCity;

public class TestAShufflePermissionCards {
	Region plains;
	Player p;
	
	@Before
	public void setUp() throws Exception {
		List<City> cities = new ArrayList<City>();
		cities.add(new City(Color.CYAN, "Arkon", new RewardCity()));
		cities.add(new City(Color.YELLOW, "Burgen", new RewardCity()));
		cities.add(new City(Color.GRAY, "Castrum", new RewardCity()));
		cities.add(new City(Color.GRAY, "Dorful", new RewardCity()));
		cities.add(new City(Color.RED, "Esti", new RewardCity()));
		plains = new Region("plains", cities, null, 2);
	}

	@Test
	public void testIsMain() throws IllegalActionException{
		p = new Player(5, 3, 0, 3, null, 0, 0);
		Action a = new AShufflePermissionCards(p, plains);
		assertEquals(false, a.isMain());
	}
	
	@Test
	public void testOk() throws IllegalActionException {
		p = new Player(5, 3, 0, 3, null, 0, 0);
		PermissionCard p1 = plains.getPermissionCard(0);
		PermissionCard p2 = plains.getPermissionCard(1);
		
		Action a = new AShufflePermissionCards(p, plains);
		a.execute();
		assertNotEquals(p1, plains.getPermissionCard(0));
		assertNotEquals(p2, plains.getPermissionCard(1));
		
		assertEquals(p.getAssistants().getAmount(), 2);
	}
	
	@Test(expected = IllegalActionException.class)
	public void testKo() throws IllegalActionException {
		p = new Player(5, 0, 0, 3, null, 0, 0);
		Action a = new AShufflePermissionCards(p, plains);
		a.execute();
	}

}
