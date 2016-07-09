package server.model.action;

import server.model.player.Assistants;
import server.model.player.Player;

/**
 * An Action that is used by this Player to buy a new MainAction for 3
 * Assistants.
 * <p>
 * This is an ExtraAction.
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
	 * {@link Action MainAction}; it will throw an exception if the Action
	 * conditions are not satisfied.
	 * 
	 * @param player
	 *            the Player who wants to buy a new MainAction
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

	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(ACTIONCOST);
		player.increaseMainAction();
		player.doExtraAction();
	}
}