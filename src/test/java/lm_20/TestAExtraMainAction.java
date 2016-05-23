package lm_20;

import static org.junit.Assert.*;

import org.junit.Test;

import game.action.AExtraMainAction;
import game.action.Action;
import game.exceptions.IllegalActionException;
import game.player.Player;

public class TestAExtraMainAction {
	Player p;

	@Test
	public void testIsMain() throws IllegalActionException{
		p = new Player(5, 3, 0, 3, null, 0, 0);
		Action a = new AExtraMainAction(p);
		assertEquals(false, a.isMain());
	}
	
	@Test
	public void testOk() throws IllegalActionException{
		p = new Player(5, 3, 0, 3, null, 0, 0);
		Action a = new AExtraMainAction(p);
		a.execute();
		assertEquals(p.getMainActionsLeft(), 2);
		assertEquals(p.getAssistants().getAmount(), 0);
	}
	
	@Test(expected = IllegalActionException.class)
	public void testKo() throws IllegalActionException{
		p = new Player(5, 2, 0, 3, null, 0, 0);
		Action a = new AExtraMainAction(p);
		a.execute();
	}

}
