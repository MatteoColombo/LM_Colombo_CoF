package server.model.action;

import server.model.TurnManager;
import server.model.player.Player;

/**
 * This is the action which is used when a player wants to end its turn without
 * doing all the actions that he is permitted. This work only if all the
 * player's main actions are done
 * 
 * @author Matteo Colombo
 *
 */
public class AEndTurn extends Action {
	private TurnManager turnManager;

	/**
	 * This method checks if all the main action were done, in case it allows
	 * the Action to be created. Otherwise it throws an exception
	 * 
	 * @param player
	 * @param turnManager
	 * @throws IllegalActionException
	 */
	public AEndTurn(Player player, TurnManager turnManager) throws IllegalActionException {
		this.turnManager = turnManager;
		if (player.getMainActionsLeft() > 0)
			throw new IllegalActionException("You must complete all your main actions before you can end the turn");
	}

	/**
	 * This method is called by the turn manager and it is used to say that the
	 * player decided to end his turn.
	 */
	@Override
	public void execute() {
		turnManager.setWantToEnd();
	}

}
