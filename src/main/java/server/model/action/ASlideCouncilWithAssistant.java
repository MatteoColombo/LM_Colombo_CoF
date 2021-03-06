package server.model.action;

import java.awt.Color;

import server.model.board.council.Council;
import server.model.board.council.CouncilorColorAvailability;
import server.model.board.council.CouncilorPool;
import server.model.player.Assistants;
import server.model.player.Player;

/**
 * An Action that is used by this Player to add a new Councillor in a Council of
 * his choice, making it slide and removing the oldest one for 1 Assistant.
 * <p>
 * This is an ExtraAction.
 * 
 * @see Action
 * @see Assistants
 * @see Council
 * @see Councillor
 * @see Player
 */
public class ASlideCouncilWithAssistant extends Action {
	private static final int ACTIONCOST = 1;
	private Player player;
	private Color councilorColor;
	private Council council;
	private CouncilorPool pool;

	/**
	 * Checks if the {@link Player} has chosen an
	 * {@link CouncilorColorAvailability available Color} for the
	 * {@link Councillor} he wants from the {@link CouncilorPool} to be added to
	 * the chosen {@link Council}; it will throw an exception if the
	 * {@link Action} conditions are not satisfied.
	 * 
	 * @param player
	 *            the Player who wants to add a new Councillor
	 * @param pool
	 *            the pool of Councillor where the Player wants take the one
	 * @param council
	 *            the Council where the Player wants to add the new Councillor
	 * @param color
	 *            the Color the Player wants for the Councillor
	 * @throws IllegalActionException
	 * @see ASlideCouncilWithAssistant
	 */
	public ASlideCouncilWithAssistant(Player player, CouncilorPool pool, Council council, Color color)
			throws IllegalActionException {
		super(false, player);
		this.player = player;
		this.pool = pool;
		this.council = council;
		this.councilorColor = color;
		if (player.getAssistants().getAmount() < ACTIONCOST) {
			throw new IllegalActionException("you can not afford it!");
		}

		if (!pool.isAvailable(councilorColor)) {
			throw new IllegalActionException("there are no more councilor available of the choosen color");
		}

	}

	@Override
	public void execute() {
		pool.slideCouncilor(council, councilorColor);
		player.getAssistants().decreaseAmount(ACTIONCOST);
		player.doExtraAction();
	}
}
