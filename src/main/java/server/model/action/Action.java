package server.model.action;

import server.model.player.Player;

/**
 * A class that represents all the Actions this Player can do during his turn.
 * <p>
 * Each of them {@link #isMain() can be a main or an extra} Action, both with
 * different specific {@link #checkLegal(Player) conditions to be satisfied} to
 * be able to {@link #execute() be performed} by the Player.
 *
 * @see Player
 */
public abstract class Action {
	private boolean main;

	/**
	 * Empty Action initialization.
	 * 
	 * @see Action
	 */
	public Action() {
		// empty
	}

	/**
	 * Initializes the Action with this {@link Player} and if this Action it's
	 * main or extra. Then it will check if the Player's conditions are
	 * satisfied, otherwise it will throw an exception.
	 * 
	 * @param main
	 *            the type of the Action
	 * @param player
	 *            the Player who wants to perform the Action
	 * @throws IllegalActionException
	 * @see Action
	 */
	public Action(boolean main, Player player) throws IllegalActionException {
		this.main = main;
		if (!checkLegal(player)) {
			throw new IllegalActionException("you can not do more action of this type");
		}
	}

	/**
	 * Returns if the Action is or not main.
	 * 
	 * @return <code>true</code> if the action is main, <code>false</code>
	 *         otherwise
	 * @see Action
	 */
	public boolean isMain() {
		return main;
	}

	/**
	 * Executes the current Action.
	 * 
	 * @see Action
	 */
	public abstract void execute();

	/**
	 * Returns if the Player's conditions are satisfied.
	 * 
	 * @param p
	 *            the Player who wants to perform the Action
	 * @return <code>true</code> if the action conditions are satisfied,
	 *         <code>false</code> otherwise
	 * @see Action
	 */
	public boolean checkLegal(Player p) {
		if (main && p.getMainActionsLeft() > 0) {
			return true;
		}

		if (!main && !p.getIfExtraActionDone()) {
			return true;
		}

		return false;
	}
}
