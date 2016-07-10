package lm_20;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import server.model.action.AExtraMainAction;
import server.model.action.Action;
import server.model.action.IllegalActionException;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.board.nobility.TrackXMLFileException;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.player.Player;

public class TestAExtraMainAction {
	Player p;
	
	@Before
	public void setUp() throws ConfigurationErrorException, TrackXMLFileException{
		Configuration config = new Configuration();
		NobilityTrack track = new NobilityTrack(
				new NobilityLoader(new Configuration().getNobility()).getNobilityTrack());
		p = new Player(config,track);
		p.getCoins().increaseAmount(5);
		p.getAssistants().increaseAmount(3);
	}

	@Test
	public void testIsMain() throws IllegalActionException{
		Action a = new AExtraMainAction(p);
		assertEquals(false, a.isMain());
	}
	
	@Test
	public void testOk() throws IllegalActionException{
		Action a = new AExtraMainAction(p);
		a.execute();
		assertEquals(p.getMainActionsLeft(), 2);
		assertEquals(p.getAssistants().getAmount(), 0);
	}
	
	@Test(expected = IllegalActionException.class)
	public void testKo() throws IllegalActionException{
		p.getAssistants().decreaseAmount(1);
		Action a = new AExtraMainAction(p);
		a.execute();
	}

}
