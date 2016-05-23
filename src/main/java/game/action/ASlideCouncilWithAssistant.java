package game.action;

import java.awt.Color;

import game.board.council.Council;
import game.board.council.CouncilorPool;
import game.exceptions.IllegalActionException;
import game.player.Player;

public class ASlideCouncilWithAssistant extends Action{
	
	private static final int ACTIONCOST = 3;
	
	private Player player;
	private Color councilorColor;
	private Council council;
	private CouncilorPool pool;
	
	public ASlideCouncilWithAssistant(Player player, CouncilorPool pool, Council council, Color color) throws IllegalActionException{
		super(false);
		this.player = player;
		this.pool = pool;
		this.council = council;
		this.councilorColor = color;
		if(player.getAssistants().getAmount() < ACTIONCOST) {
			throw new IllegalActionException("you can not afford it!");
		}
		
		if(!pool.isAvailable(councilorColor)) {
			throw new IllegalActionException("there are no more councilor available of the choosen color");
		}
	
	}

	@Override
	public void execute() {
		pool.slideCouncilor(council, councilorColor);
		player.getAssistants().decreaseAmount(ACTIONCOST);
		player.doExtraAction();
	}
}
