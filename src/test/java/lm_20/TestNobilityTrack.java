package lm_20;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Configuration;
import model.board.nobility.NobilityLoader;
import model.board.nobility.NobilityTrack;
import model.exceptions.ConfigurationErrorException;
import model.exceptions.TrackXMLFileException;
import model.player.Player;

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
		player.getNoblePoints().increaseAmount(2);
		assertEquals(2, player.getVictoryPoints().getAmount());
		assertEquals(12, player.getCoins().getAmount());
	}

}
