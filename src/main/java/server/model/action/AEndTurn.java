package server.model.action;

import server.model.TurnManager;
import server.model.player.Player;

/**
 * An Action that is used by this Player when he wants to end his turn without
 * doing the ExtraAction that he could have done; this works only if all the
 * Player's MainActions are already done.
 * 
 * @author Matteo Colombo
 * @see Action
 * @see Player
 * @see TurnManager
 */
public class AEndTurn extends Action {
	private TurnManager turnManager;

	/**
	 * Checks if the {@link Player} has done all his {@link Action MainActions};
	 * it will throw an exception if the Action conditions are not satisfied.
	 * 
	 * @param player
	 *            the Player who wants to end his turn
	 * @param turnManager
	 *            the TurnManager of the Game
	 * @throws IllegalActionException
	 */
	public AEndTurn(Player player, TurnManager turnManager) throws IllegalActionException {
		this.turnManager = turnManager;
		if (player.getMainActionsLeft() > 0)
			throw new IllegalActionException("You must complete all your main actions before you can end the turn");
	}

	@Override
	public void execute() {
		turnManager.setWantToEnd();
	}

}
