package game.action;

import java.awt.Color;

import game.board.Council;
import game.board.CouncilorPool;
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
		
		if(player.getAssistants().getAmount() < ACTIONCOST) {
			throw new IllegalActionException("you can not afford it!");
		}
		
		if(!pool.isAvailable(councilorColor)) {
			throw new IllegalActionException("there are no more councilor available of the choosen color");
		}
		
		pool.slideCouncilor(council, councilorColor);
		player.getAssistants().decrease(ACTIONCOST);
	}
}
