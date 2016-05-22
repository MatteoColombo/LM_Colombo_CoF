package lm_20;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import game.board.ColorConstants;
import game.board.Region;
import game.board.city.*;
import game.board.council.*;
import game.reward.RewardCity;

public class TestRegion {
	Region region;
	List<City> cities = new ArrayList<City>();
	Council council;
	@Before
	public void setUp() throws Exception {
		cities.add(new City(ColorConstants.SAPPHIRE, "Arkon", new RewardCity()));
		cities.add(new City(ColorConstants.GOLD, "Burgen", new RewardCity()));
		cities.add(new City(ColorConstants.SILVER, "Castrum", new RewardCity()));
		cities.add(new City(ColorConstants.SILVER, "Dorful", new RewardCity()));
		cities.add(new City(ColorConstants.BRONZE, "Esti", new RewardCity()));
		
		List<Councilor> members = new ArrayList<Councilor>();
		members.add(new Councilor(ColorConstants.BLACK));
		members.add(new Councilor(ColorConstants.WHITE));
		members.add(new Councilor(ColorConstants.ORANGE));
		members.add(new Councilor(ColorConstants.PURPLE));
	
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
	public void testCity() {
		City castrum = cities.get(2);
		assertEquals(castrum.getColor(), ColorConstants.SILVER);
		assertEquals(castrum.isCapital(), false);
		assertEquals(castrum.getNumberOfEmporium(), 0);
		assertEquals(castrum.isConnectedTo(cities.get(3)), false);
	}

}
