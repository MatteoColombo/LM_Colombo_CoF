package server.model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.Serializable;

import server.model.board.city.City;
import server.model.market.Soldable;
import server.model.reward.Bonus;
import server.model.reward.Reward;
import server.model.reward.RewardPermission;

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
public class PermissionCard implements Soldable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8717409237659947557L;
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
		this.used=false;
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
		this.cities = new ArrayList<>();
		Random r = new Random();
		do {
			for (City x : citiesOfRegions) {
				if (r.nextBoolean())
					this.cities.add(x);
				if(cities.size()== 3)
					break;
			}
			empty = this.cities.isEmpty();
		} while (empty);
		this.reward = new RewardPermission();
		this.used=false;
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

	/**
	 * 
	 */
	@Override
	public boolean equals(Object item) {
		if(!(item instanceof PermissionCard))
			return false;
		PermissionCard card=(PermissionCard)item;
		if(this.cities.equals(card.cities) && this.reward.equals(card.reward) && this.used == card.used)
			return true;
		return false;
	}
	
	@Override
	public int hashCode(){
		int sum= 100*cities.size();
		sum+= 5*reward.getGeneratedRewards().size();
		return sum;
	}

}