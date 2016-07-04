package lm_20;

import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.Color;

import server.model.board.city.City;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.TrackXMLFileException;
import server.model.player.Player;
import server.model.reward.Reward;
import server.model.reward.RewardCity;

public class TestCity {
	
	@Test
	public void testGetter() {
		Reward reward = new RewardCity();
		City castrum = new City(Color.GRAY, "Castrum", reward);
		assertEquals(castrum.getName(), "Castrum");
		assertEquals(castrum.getColor(), Color.GRAY);
		assertEquals(castrum.isCapital(), false);
		assertEquals(castrum.getNumberOfEmporium(), 0);
		assertEquals(castrum.getReward(), reward);
	}
	
	@Test
	public void testConnections() {
		City castrum = new City(Color.GRAY, "Castrum", new RewardCity());
		City milan = new City(Color.YELLOW, "Milan", new RewardCity());
		City rome = new City(Color.BLUE, "Milan", new RewardCity());
		
		castrum.addConnection(milan);
		assertEquals(castrum.isConnectedTo(milan), true);
		assertEquals(castrum.isConnectedTo(rome), false);
		assertEquals(castrum.getConnectedCities().get(0), milan);
	}
	
	@Test
	public void testEmporium() throws TrackXMLFileException, ConfigurationErrorException {
		Configuration config= new Configuration();
		City castrum = new City(Color.GRAY, "Castrum", new RewardCity());
		NobilityTrack track= new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack());
		Player p = new Player(config,track);
		castrum.addEmporium(p.getEmporium().remove(0));
		assertEquals(castrum.hasEmporiumOfPlayer(p), true);
		assertEquals(castrum.getNumberOfEmporium(), 1);
	}

}
