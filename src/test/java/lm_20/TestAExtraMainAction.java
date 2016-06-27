package lm_20;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.action.AExtraMainAction;
import server.model.action.Action;
import server.model.action.IllegalActionException;
import server.model.player.Player;

public class TestAExtraMainAction {
	Player p;

	@Test
	public void testIsMain() throws IllegalActionException{
		p = new Player(5, 3, 0, 3, null, 0, 0,null,null);
		Action a = new AExtraMainAction(p);
		assertEquals(false, a.isMain());
	}
	
	@Test
	public void testOk() throws IllegalActionException{
		p = new Player(5, 3, 0, 3, null, 0, 0,null,null);
		Action a = new AExtraMainAction(p);
		a.execute();
		assertEquals(p.getMainActionsLeft(), 2);
		assertEquals(p.getAssistants().getAmount(), 0);
	}
	
	@Test(expected = IllegalActionException.class)
	public void testKo() throws IllegalActionException{
		p = new Player(5, 2, 0, 3, null, 0, 0,null,null);
		Action a = new AExtraMainAction(p);
		a.execute();
	}

}
