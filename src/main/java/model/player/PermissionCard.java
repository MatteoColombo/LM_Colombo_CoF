package model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.board.city.City;
import model.reward.*;

/**
 * A class that represents every PermissionCard in the game.
 * <p>
 * Each of them has different features that form its own Bonuses, such as: the
 * {@link #getCardReward() Reward} that is given to the Player who obtains that
 * PermissionCard, the {@link #getCardCity() Cities} where it can be set an
 * Emporium after {@link #setCardUsed() it has been used} and if
 * {@link #getIfCardUsed() it has already been employed or not}.
 * 
 * @author Davide Cavallini
 * @see Bonus
 * @see City
 * @see Emporium
 * @see Player
 * @see Reward
 */
public class PermissionCard {
	private List<City> cities;
	private Reward reward;
	private boolean used;

	/**
	 * Initializes the PermissionCard with a {@link Reward} and a list of
	 * {@link City Cities}, where it can be used, that have already been chosen.
	 * 
	 * @param cities
	 *            the list of Cities that will be assigned to this
	 *            PermissionCard where it'll be able to be used
	 * @param reward
	 *            the Reward that will be assigned to this PermissionCard
	 * @see PermissionCard
	 */
	public PermissionCard(List<City> cities, Reward reward) {
		this.cities = cities;
		this.reward = reward;
	}

	/**
	 * Initializes the PermissionCard with the {@link City Cities} (at least
	 * one) where it can be used; this is done randomly choosing between all the
	 * possible given Cities.
	 * <p>
	 * A {@link Reward} is also generated and assigned to this PermissionCard.
	 * 
	 * @param citiesOfRegions
	 *            the list of all the possible Cities where this PermissionCard
	 *            could be used
	 * @see PermissionCard
	 * @see Random
	 */
	public PermissionCard(List<City> citiesOfRegions) {
		boolean empty;
		cities = new ArrayList<>();
		Random r = new Random();
		do {
			for (City x : citiesOfRegions) {
				boolean i = r.nextBoolean();
				if (i)
					this.cities.add(x);
			}
			empty = this.cities.isEmpty();
		} while (empty);
		reward = new RewardPermission();
	}

	/**
	 * Returns the {@link City Cities} where it's possible to use this
	 * PermissionCard.
	 * 
	 * @return the list of Cities where this PermissionCard can be used
	 * @see PermissionCard
	 */
	public List<City> getCardCity() {
		return this.cities;
	}

	/**
	 * Returns the {@link Reward} assigned to this PermissionCard.
	 * 
	 * @return the Reward of this PermissionCard
	 * @see PermissionCard
	 */
	public Reward getCardReward() {
		return this.reward;
	}

	/**
	 * Returns if this PermissionCard has already been used or not by a
	 * {@link Player}.
	 * 
	 * @return <code>true</code> if this PermissionCard has already been used;
	 *         <code>false</code> otherwise
	 * @see PermissionCard
	 */
	public boolean getIfCardUsed() {
		return this.used;
	}

	/**
	 * Sets this PermissionCard as "already used" by a {@link Player}.
	 * 
	 * @see PermissionCard
	 */
	public void setCardUsed() {
		this.used = true;
	}

}