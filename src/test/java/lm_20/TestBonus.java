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
import server.model.reward.BAssistants;
import server.model.reward.Bonus;

/**
 * 
 * @author Davide Cavallini
 *
 */
public class TestBonus {
	private Player p;

	@Before
	public void initialization() throws TrackXMLFileException, ConfigurationErrorException {
		Configuration config= new Configuration();
		p= new Player(config, new NobilityTrack(new NobilityLoader(config.getNobility()).getNobilityTrack()));
		p.getCoins().increaseAmount(10);
		p.getAssistants().increaseAmount(1);
	}

	@Test
	public void testBAssistantsConstructor() {
		BAssistants ba = new BAssistants(10);
		assertEquals(10, ba.getAmount());
	}
	@Test
	public void testBAssistantsMethod_newCopy() {
		BAssistants ba1 = new BAssistants(10);
		Bonus ba2 = ba1.newCopy(10);		
		assertEquals(ba2.getAmount(), ba1.getAmount());
	}

	@Test
	public void testBAssistantsMethod_assignBonusTo() {
		BAssistants ba = new BAssistants(10);
		ba.assignBonusTo(p);		
		assertEquals(11, p.getAssistants().getAmount());
	}
}