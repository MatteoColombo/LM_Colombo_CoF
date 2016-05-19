package game.action;

import java.awt.Color;

import game.board.Council;
import game.board.CouncilorPool;
import game.board.exceptions.NegativeException;
import game.player.Player;

public class ASlideCouncilWithAssistant extends Action{
	
	private static final int ACTIONCOST = 1;
	
	private Player player;
	private Color councilorColor;
	private Council council;
	private CouncilorPool pool;
	
	public ASlideCouncilWithAssistant(Player player, CouncilorPool pool, Council council, Color color) {
		super(false);
		this.player = player;
		this.pool = pool;
		this.council = council;
		this.councilorColor = color;
	}

	@Override
	public void execute() throws IllegalActionException {
		try {
			player.getAssistants().decrease(ACTIONCOST);
		} catch(NegativeException e) {
			throw new IllegalActionException();
		}
		
		if(pool.isAvailable(councilorColor)) {
			pool.slideCouncilor(council, councilorColor);
		} else {
			throw new IllegalActionException();
		}
	}
}
