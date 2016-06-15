package model.action;

import java.awt.Color;

import model.board.council.Council;
import model.board.council.CouncilorPool;
import model.exceptions.IllegalActionException;
import model.player.Player;

public class ASlideCouncilWithAssistant extends Action{
	
	private static final int ACTIONCOST = 3;
	
	private Player player;
	private Color councilorColor;
	private Council council;
	private CouncilorPool pool;
	
	public ASlideCouncilWithAssistant(Player player, CouncilorPool pool, Council council, Color color) throws IllegalActionException{
		super(false, player);
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
