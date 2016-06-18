package model.action;

import model.exceptions.IllegalActionException;
import model.player.*;

/**
 * An Action that is used by this Player to {@link #execute() buy a new main
 * Action} for 3 Assistants.
 * <p>
 * This is an extra Action.
 * 
 * @see Action
 * @see Assistants
 * @see Player
 */
public class AExtraMainAction extends Action {
	private static final int ACTIONCOST = 3;
	private Player player;

	/**
	 * Checks if the {@link Player} has enough {@link Assistants} to buy a new
	 * main {@link Action}; it will throw an exception if the Action conditions
	 * are not satisfied.
	 * 
	 * @param player
	 *            the Player who wants to buy a new main Action
	 * @throws IllegalActionException
	 * @see AExtraMainAction
	 */
	public AExtraMainAction(Player player) throws IllegalActionException {
		super(false, player);
		if (player.getAssistants().getAmount() < ACTIONCOST) {
			throw new IllegalActionException("you can not afford it!");
		}
		this.player = player;
	}

	/**
	 * Executes the current {@link Action}.
	 * 
	 * @see AExtraMainAction
	 */
	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(ACTIONCOST);
		player.increaseMainAction();
	}
}