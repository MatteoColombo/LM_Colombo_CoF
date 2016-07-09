package server.model.action;

import server.model.board.Region;
import server.model.player.Assistants;
import server.model.player.Player;

/**
 * An Action that is used by this Player to shuffle the faced-up PermissionCards
 * of a Region for 1 Assistant.
 * <p>
 * This is an ExtraAction.
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

	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(ACTIONCOST);
		region.shufflePermissionCards();
		player.doExtraAction();
	}
}
