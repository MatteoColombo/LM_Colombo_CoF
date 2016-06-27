package server.model.board.council;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import server.model.action.IllegalActionException;
import server.model.board.King;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;

/**
 * A class that represent all the Councils of the Map.
 * <p>
 * Each Council is composed by an amount of {@link #getCouncilorsColor()
 * Councilors} that works like a {@link #getHeadColor() Color queue} (every time
 * a new {@link #insertCouncilor(Councilor) one is added}, the oldest one is
 * removed) that it is used to buy PermissionCards or to build an Emporium using
 * the King {@link #compareCardCouncil(List) using PoliticCards}.
 * 
 * @author Matteo Colombo
 * @see Color
 * @see Councilor
 * @see CouncilorColorAvailability
 * @see CouncilorPool
 * @see King
 * @see PermissionCard
 * @see PoliticCard
 */

public class Council implements Serializable {
	private static final long serialVersionUID = -8263548205552114196L;
	private List<Councilor> councMembers;

	/**
	 * Initializes the Council with a list of the initial {@link Councilor
	 * Councilors}.
	 * 
	 * @param councMember
	 *            the list of initial Councilors
	 * @see Council
	 */
	public Council(List<Councilor> councMembers) {
		this.councMembers = councMembers;
	}

	/**
	 * Creates a copy from another Council.
	 * 
	 * @param copy
	 *            the Council to be copied
	 * @see Council
	 */
	public Council(Council copy) {
		this.councMembers = new ArrayList<>();
		for (Color color : copy.getCouncilorsColor()) {
			Councilor counc = new Councilor(color);
			councMembers.add(counc);
		}
	}

	/**
	 * Removes the first and oldest {@link Councilor} in this Council and adds
	 * the received one in tail.
	 * 
	 * @param councilor
	 *            the Councilor which is going to be added in tail
	 * @see Council
	 */
	public void insertCouncilor(Councilor councilor) {
		councMembers.remove(0);
		councMembers.add(councilor);
	}

	/**
	 * Returns the list of the {@link Color Colors} of the {@link Councilor
	 * Councilors} in this Council.
	 * 
	 * @return the list of all the Colors of the Councilors in this Council
	 * @see Council
	 */
	public List<Color> getCouncilorsColor() {
		List<Color> colorList = new ArrayList<>();
		for (Councilor temp : councMembers)
			colorList.add(temp.getColor());
		return colorList;
	}

	/**
	 * Returns the {@link Color} of the {@link Councilor} in the head of the
	 * queue.
	 * 
	 * @return the Color of the head Councilor of this Council
	 * @see Council
	 */
	public Color getHeadColor() {
		return councMembers.get(0).getColor();
	}

	/**
	 * Returns the number of {@link Councilor Councilors} not covered by the
	 * {@link PoliticCard PoliticCards} checking first if there are more than 4
	 * cards, then comparing the not-multicolored PoliticCards with the
	 * Councilors and last counting the number of multicolored PoliticCards.
	 * 
	 * @param cards
	 *            a list of PoliticCards
	 * @return the number of uncovered Councilors
	 * @throws IllegalActionException
	 * @see Council
	 */
	public int compareCardCouncil(List<PoliticCard> cards) throws IllegalActionException {
		List<Councilor> comparableCouncilors = new ArrayList<>(this.councMembers);
		checkCardsNumber(cards);
		for (PoliticCard card : cards) {
			boolean found = false;
			if (card.isMultipleColor())
				continue;
			for (int i = 0; i < comparableCouncilors.size(); i++)
				if (comparableCouncilors.get(i).getColor().equals(card.getCardColor())) {
					found = true;
					comparableCouncilors.remove(i);
					break;
				}
			if (!found)
				throw new IllegalActionException("Invalid card/cards");
		}
		for (PoliticCard card : cards) {
			if (card.isMultipleColor()) {
				comparableCouncilors.remove(0);
			}
		}

		return comparableCouncilors.size();
	}

	/**
	 * Checks if the number of {@link PoliticCard PoliticCards} that a
	 * {@link Player} wants to use to satisfy a Council is greater than the
	 * number of the {@link Councilor Councilors} in the Council, throwing an
	 * exception in that case.
	 * 
	 * @param cards
	 *            a list of PoliticCards
	 * @throws IllegalActionException
	 * @see Council
	 */
	private void checkCardsNumber(List<PoliticCard> cards) throws IllegalActionException {
		if (cards.size() > this.councMembers.size())
			throw new IllegalActionException("Too many cards");
	}

}
