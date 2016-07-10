package lm_20;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.board.nobility.TrackXMLFileException;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.player.Player;

public class TestNobilityTrack {
	private Player player;
	@Before
	public void test() throws ConfigurationErrorException, TrackXMLFileException {
		Configuration config= new Configuration();
		this.player= new Player(config, new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack()));
		player.getCoins().increaseAmount(10);
		player.getAssistants().increaseAmount(1);
		player.getVictoryPoints().increaseAmount(1);
	}
	
	@Test
	public void testIncreaseByTwoPoints(){
		player.getNobility().increaseAmount(2);
		assertEquals(2, player.getVictoryPoints().getAmount());
		assertEquals(12, player.getCoins().getAmount());
		
	}

}
