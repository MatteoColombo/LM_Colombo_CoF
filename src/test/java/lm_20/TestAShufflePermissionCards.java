package lm_20;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.action.AShufflePermissionCards;
import game.action.Action;
import game.board.ColorConstants;
import game.board.Region;
import game.board.city.City;
import game.exceptions.IllegalActionException;
import game.player.PermissionCard;
import game.player.Player;
import game.reward.RewardCity;

public class TestAShufflePermissionCards {
	Region plains;
	Player p;
	
	@Before
	public void setUp() throws Exception {
		List<City> cities = new ArrayList<City>();
		cities.add(new City(ColorConstants.SAPPHIRE, "Arkon", new RewardCity()));
		cities.add(new City(ColorConstants.GOLD, "Burgen", new RewardCity()));
		cities.add(new City(ColorConstants.SILVER, "Castrum", new RewardCity()));
		cities.add(new City(ColorConstants.SILVER, "Dorful", new RewardCity()));
		cities.add(new City(ColorConstants.BRONZE, "Esti", new RewardCity()));
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
