package server.model.action;

import server.model.player.Assistants;
import server.model.player.Coins;
import server.model.player.Player;

/**
 * An Action that is used by this Player to {@link #execute() buy an Assistants}
 * for 3 Coins.
 * <p>
 * This is an extra Action.
 * 
 * @see Action
 * @see Assistants
 * @see Coins
 * @see Player
 */
public class ABuyAssistant extends Action {
	private static final int ASSISTANTPRICE = 3;
	private Player player;

	/**
	 * Checks if the {@link Player} has enough {@link Coins} to buy an
	 * {@link Assistants Assistant}; it will throw an exception if the
	 * {@link Action} conditions are not satisfied.
	 * 
	 * @param player
	 *            the Player who wants to buy an Assistants
	 * @throws IllegalActionException
	 * @see ABuyAssistant
	 */
	public ABuyAssistant(Player player) throws IllegalActionException {
		super(false, player);
		this.player = player;
		if (player.getCoins().getAmount() < ASSISTANTPRICE) {
			throw new IllegalActionException("you can not afford it!");
		}
	}

	/**
	 * Executes the current {@link Action}.
	 * 
	 * @see ABuyAssistant
	 */
	@Override
	public void execute() {
		player.getCoins().decreaseAmount(ASSISTANTPRICE);
		player.getAssistants().increaseAmount(1);
		player.doExtraAction();
	}
}
