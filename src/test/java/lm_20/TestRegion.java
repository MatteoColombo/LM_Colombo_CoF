package lm_20;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.board.Region;
import model.board.city.*;
import model.board.council.*;
import model.player.Player;
import model.reward.RewardCity;

public class TestRegion {
	Region region;
	List<City> cities = new ArrayList<City>();
	Council council;
	@Before
	public void setUp() throws Exception {
		cities.add(new City(Color.AQUA, "Arkon", new RewardCity()));
		cities.add(new City(Color.GOLD, "Burgen", new RewardCity()));
		cities.add(new City(Color.SILVER, "Castrum", new RewardCity()));
		cities.add(new City(Color.SILVER, "Dorful", new RewardCity()));
		cities.add(new City(Color.BROWN, "Esti", new RewardCity()));
		
		List<Councilor> members = new ArrayList<Councilor>();
		members.add(new Councilor(Color.BLACK));
		members.add(new Councilor(Color.WHITE));
		members.add(new Councilor(Color.ORANGE));
		members.add(new Councilor(Color.PINK));
	
		council = new Council(members);	
	}

	@Test
	public void testGetter() {
		region = new Region("sea", cities, council, 2);
		assertEquals("sea", region.toString());
		assertEquals(council, region.getCouncil());
		assertEquals(cities, region.getCities());
		assertEquals(cities.get(1), region.getCity("Burgen"));
	}
	
	@Test
	public void testCompleted() {
		
		region = new Region("sea", cities, council, 3);
		Player p = new Player(0, 0, 0, 10, null, 0, 0);
		assertEquals(region.isCompleted(p), false);
		
		Player p2 = new Player(0, 0, 0, 10, null, 0, 0);
		region.getCities().get(3).addEmporium(p2.getEmporium().remove(0));
		region.getCities().get(0).addEmporium(p2.getEmporium().remove(0));	
		assertEquals(region.isCompleted(p2), false);
		
		for(City city: region.getCities()) {
			city.addEmporium(p.getEmporium().remove(0));
		}	
		assertEquals(region.isCompleted(p), true);
	}
}
