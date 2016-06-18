package model.action;

import java.awt.Color;

import model.board.council.*;
import model.exceptions.IllegalActionException;
import model.player.*;

/**
 * An Action that is used by this Player to {@link #execute() add a new Councillor}
 * in a Council of his choice, making it slide, removing the oldest one and
 * giving the Player 4 Coins.
 * <p>
 * This is a main Action.
 * 
 * @see Action
 * @see Coins
 * @see Council
 * @see Councillor
 * @see Player
 */
public class ASlideCouncil extends Action {
	private static final int GAIN = 4;
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
	 * @see ASlideCouncil
	 */
	public ASlideCouncil(Player player, CouncilorPool pool, Council council, Color color)
			throws IllegalActionException {
		super(true, player);
		this.player = player;
		this.pool = pool;
		this.council = council;
		this.councilorColor = color;
		if (!pool.isAvailable(councilorColor)) {
			throw new IllegalActionException("there are no more councilor available of the choosen color");
		}
	}

	/**
	 * Executes the current {@link Action}.
	 * 
	 * @see ASlideCouncil
	 */
	@Override
	public void execute() {
		pool.slideCouncilor(council, councilorColor);
		player.getCoins().increaseAmount(GAIN);
		player.doMainAction();
	}
}
