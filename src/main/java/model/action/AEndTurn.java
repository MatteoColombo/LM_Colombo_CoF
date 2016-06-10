package model.action;

import model.TurnManager;
import model.exceptions.IllegalActionException;
import model.player.Player;

public class AEndTurn extends Action{
	private TurnManager turnManager;
	public AEndTurn(Player player, TurnManager turnManager) throws IllegalActionException {
		this.turnManager=turnManager;
		if(player.getMainActionsLeft()>0)
			throw new IllegalActionException("You must complete all your main actions before you can end the turn");
	}
	@Override
	public void execute() {
		turnManager.setWantToEnd();
	}

}
