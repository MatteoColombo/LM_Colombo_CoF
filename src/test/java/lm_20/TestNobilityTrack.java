package lm_20;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.TrackXMLFileException;
import server.model.player.Player;

public class TestNobilityTrack {
	private Player player;
	private NobilityTrack track;
	@Before
	public void test() throws ConfigurationErrorException, TrackXMLFileException {
		Configuration config= new Configuration();
		track= new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack());
		player = new Player(10, 1, 6, 10, config.getColorsList(), 0, 0, track, null);
	}
	
	@Test
	public void testIncreaseByTwoPoints(){
		player.getNobilityPoints().increaseAmount(2);
		assertEquals(2, player.getVictoryPoints().getAmount());
		assertEquals(12, player.getCoins().getAmount());
	}

}
