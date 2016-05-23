package game.action;

import game.exceptions.IllegalActionException;
import game.player.Player;

public class AExtraMainAction extends Action {
	private static final int ACTIONCOST = 3;
	
	private Player player;
	
	public AExtraMainAction(Player player) throws IllegalActionException{
		super(false);
		if(player.getAssistants().getAmount() < ACTIONCOST) {
			throw new IllegalActionException("you can not afford it!");
		}
		this.player = player;
	}
	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(ACTIONCOST);
		player.increaseMainAction();
	}
}