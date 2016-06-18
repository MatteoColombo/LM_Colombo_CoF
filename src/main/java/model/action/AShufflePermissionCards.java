package model.action;

import model.board.Region;
import model.exceptions.IllegalActionException;
import model.player.Assistants;
import model.player.Player;

/**
 * An Action that is used by this Player to {@link #execute() shuffle the faced-up
 * PermissionCards} of a Region for 1 Assistant.
 * <p>
 * This is an extra Action.
 * 
 * @see Action
 * @see Assistants
 * @see Region
 * @see Player
 */
public class AShufflePermissionCards extends Action {
	private static final int ACTIONCOST = 1;
	private Player player;
	private Region region;

	/**
	 * Checks if the {@link Player} has enough {@link Assistants} to shuffle the
	 * faced-up {@link PermissionCards} of a specific {@link Region}; it will
	 * throw an exception if the {@link Action} conditions are not satisfied.
	 * 
	 * @param player
	 *            the Player who wants to buy a new main Action
	 * @param region
	 *            the Region where shuffle the PermissionCards
	 * @throws IllegalActionException
	 * @see AShufflePermissionCards
	 */
	public AShufflePermissionCards(Player player, Region region) throws IllegalActionException {
		super(false, player);
		if (player.getAssistants().getAmount() < ACTIONCOST) {
			throw new IllegalActionException("you can not afford it!");
		}
		this.player = player;
		this.region = region;
	}

	/**
	 * Executes the current {@link Action}.
	 * 
	 * @see AShufflePermissionCards
	 */
	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(ACTIONCOST);
		region.shufflePermissionCards();
	}
}
