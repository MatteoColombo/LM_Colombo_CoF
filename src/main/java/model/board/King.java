package model.board;

import model.board.city.City;
import model.board.council.Council;
import model.player.Emporium;
import model.player.PermissionCard;
import model.player.Player;

/**
 * A class that represent the King of the Game.
 * <p>
 * The King can be {@link #moveKing(City) moved} from the capital (where he is
 * at the beginning of the Game) to {@link #getKingLocation() any City} a Player
 * want to build an Emporium without PermissionCard, but so it's
 * {@link #getKingCouncil() his own Council} to be satisfied and not one of the
 * Region ones.
 * 
 * @see City
 * @see Council
 * @see Emporium
 * @see PermissionCard
 * @see Player
 * @see Region
 */
public class King {
	private City kingLocation;
	private Council kingCouncil;

	/**
	 * Initializes the King receiving the {@link Council} of the King and the
	 * {@link City}, which is the King's first location and also the capital.
	 * 
	 * @param kingCity
	 *            the City which is the capital
	 * @param kingCouncil
	 *            the King's Council
	 * @see King
	 */
	public King(City kingCity, Council kingCouncil) {
		this.kingCouncil = kingCouncil;
		this.kingLocation = kingCity;
	}

	/**
	 * Return the current location of the King.
	 * 
	 * @return the City where the King is now
	 * @see King
	 */
	public City getKingLocation() {
		return kingLocation;

	}

	/**
	 * Moves the King from its current {@link City} to the desired one.
	 * 
	 * @param newKingCity
	 *            the City in which the Player wants to move the King
	 * @see King
	 */
	public void moveKing(City newKingCity) {
		this.kingLocation = newKingCity;
	}

	/**
	 * Returns the {@link Council King's Council}.
	 * 
	 * @return the King's Council
	 * @see King
	 */
	public Council getKingCouncil() {
		return kingCouncil;
	}
}
