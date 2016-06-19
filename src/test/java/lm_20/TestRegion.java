package lm_20;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Configuration;
import model.board.Region;
import model.board.city.*;
import model.board.council.*;
import model.board.nobility.NobilityLoader;
import model.board.nobility.NobilityTrack;
import model.exceptions.ConfigurationErrorException;
import model.exceptions.TrackXMLFileException;
import model.player.Player;
import model.reward.RewardCity;

public class TestRegion {
	Region region;
	List<City> cities = new ArrayList<City>();
	Council council;
	@Before
	public void setUp() throws Exception {
		cities.add(new City(Color.BLUE, "Arkon", new RewardCity()));
		cities.add(new City(Color.YELLOW, "Burgen", new RewardCity()));
		cities.add(new City(Color.GRAY, "Castrum", new RewardCity()));
		cities.add(new City(Color.GRAY, "Dorful", new RewardCity()));
		cities.add(new City(Color.RED, "Esti", new RewardCity()));
		
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
	public void testCompleted() throws TrackXMLFileException, ConfigurationErrorException {

		NobilityTrack track= new NobilityTrack(new NobilityLoader(new Configuration().getNobility()).getNobilityTrack());
		region = new Region("sea", cities, council, 3);
		Player p = new Player(0, 0, 0, 10, null, 0, 0, track, null);
		assertEquals(region.isCompleted(p), false);
		
		Player p2 = new Player(0, 0, 0, 10, null, 0, 0, track, null);
		region.getCities().get(3).addEmporium(p2.getEmporium().remove(0));
		region.getCities().get(0).addEmporium(p2.getEmporium().remove(0));	
		assertEquals(region.isCompleted(p2), false);
		
		for(City city: region.getCities()) {
			city.addEmporium(p.getEmporium().remove(0));
		}	
		assertEquals(region.isCompleted(p), true);
	}
}
