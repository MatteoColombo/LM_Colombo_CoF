package lm_20;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import server.model.action.AShufflePermissionCards;
import server.model.action.Action;
import server.model.action.IllegalActionException;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.reward.RewardCity;

public class TestAShufflePermissionCards {
	Region plains;
	Player p;
	NobilityTrack track;
	@Before
	public void setUp() throws Exception {
		List<City> cities = new ArrayList<City>();
		cities.add(new City(Color.CYAN, "Arkon", new RewardCity()));
		cities.add(new City(Color.YELLOW, "Burgen", new RewardCity()));
		cities.add(new City(Color.GRAY, "Castrum", new RewardCity()));
		cities.add(new City(Color.GRAY, "Dorful", new RewardCity()));
		cities.add(new City(Color.RED, "Esti", new RewardCity()));
		plains = new Region("plains", cities, null, 2);
		Configuration config = new Configuration();	
		track= new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack());
		p = new Player(config,track);
		p.getCoins().increaseAmount(5);
		p.getAssistants().increaseAmount(3);
	}

	@Test
	public void testIsMain() throws IllegalActionException{
		Action a = new AShufflePermissionCards(p, plains);
		assertEquals(false, a.isMain());
	}
	
	@Test
	public void testOk() throws IllegalActionException {
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
		p.getAssistants().decreaseAmount(3);
		Action a = new AShufflePermissionCards(p, plains);
		a.execute();
	}

}
