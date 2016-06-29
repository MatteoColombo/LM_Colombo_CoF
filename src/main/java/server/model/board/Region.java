package server.model.board;

import java.util.List;

import server.model.action.Action;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.player.Emporium;
import server.model.player.PermissionCard;
import server.model.player.Player;

/**
 * A class that represents the Regions in which the Map of the Game is divided.
 * <p>
 * {@link #toString() Each of them} have:
 * <ul>
 * <li>An equal number of {@link #getCities() Cities} where it's possible to
 * check if a Player has {@link #isCompleted(Player) placed an Emporium in all
 * of them} or simply {@link #getCity(String) retrieve their single names}, that
 * can start only with limited letters of the alphabet, according to the Region;
 * </li>
 * <li>A {@link #getPermissionSlotsNumber() number of uncovered} PermissionCards
 * that can be {@link #shufflePermissionCards() shuffled} to generate new ones,
 * {@link #getPermissionCard(int) picked} up to give it a look or
 * {@link #givePermissionCard(int) taken} if the right conditions are achieved
 * with an Action;</li>
 * <li>A {@link #getCouncil() own Council} used to gain one of the available
 * PermissonCards.</li>
 * </ul>
 *
 * @see Action
 * @see Board
 * @see City
 * @see Council
 * @see Emporium
 * @see PermissionCard
 * @see Player
 */
public class Region {
	private final int NUM_CARDS;
	private String regionName;
	private List<City> cities;
	private Council regionCouncil;
	private PermissionCard[] permissionCards;

	/**
	 * Initializes the permission cards, it receives the parameters
	 * 
	 * @param regionName
	 *            a string, the name of the region
	 * @param cities
	 *            a list of the cities located in the region
	 * @param regionCouncil
	 *            a council, the council of the region
	 * @param numDisclosedPermCards
	 *            an integer, the number of disclosed permit cards
	 * @see Region
	 */
	public Region(String regionName, List<City> cities, Council regionCouncil, int numDisclosedPermCards) {
		this.NUM_CARDS = numDisclosedPermCards;
		this.regionName = regionName;
		this.regionCouncil = regionCouncil;
		this.cities = cities;
		this.regionCouncil = regionCouncil;
		permissionCards = new PermissionCard[NUM_CARDS];
		for (int i = 0; i < permissionCards.length; i++)
			generatePermissionCard(i);
	}

	/**
	 * Returns the name of this {@link City} as a string.
	 * 
	 * @return the string name of this City
	 * @see Region
	 */
	@Override
	public String toString() {
		return regionName;
	}

	/**
	 * Checks if this {@link Player} has an {@link Emporium} in each {@link City
	 * Cities} of this Region.
	 * 
	 * @param p
	 *            the Player who has to be checked
	 * @return <code>true</code> if this Player has completed this Region,
	 *         <code>false</code> otherwise
	 * @see Region
	 */
	public boolean isCompleted(Player p) {
		for (City c : cities) {
			if (!c.hasEmporiumOfPlayer(p))
				return false;
		}
		return true;
	}

	/**
	 * Generates a {@link PermissionCard PermissionCards} and puts it the
	 * specified position of the available slots.
	 * 
	 * @param posArray
	 *            an integer, it specifies in which position the PermissionCard
	 *            has to be placed
	 * @see Region
	 */
	private void generatePermissionCard(int posArray) {
		permissionCards[posArray] = new PermissionCard(cities);
	}

	/**
	 * Generates a new {@link PermissionCard} in all the available slots.
	 * 
	 * @see Region
	 */
	public void shufflePermissionCards() {
		for (int i = 0; i < permissionCards.length; i++) {
			generatePermissionCard(i);
		}
	}

	/**
	 * Returns a specific {@link PermissionCard} without removing it from the
	 * Map. Use this if a {@link Player} want to check the PermissionCard.
	 * 
	 * @param posArray
	 *            an integer, it specifies which PermissionCard the Player want
	 *            to see
	 * @return a permission card
	 * @see Region
	 */
	public PermissionCard getPermissionCard(int posArray) {
		return permissionCards[posArray];
	}

	/**
	 * Returns a specific {@link PermissionCard} and removes it from the Map.
	 * The free slot is filled with another PermissionCard. Use this if a
	 * {@link Player} acquires a PermissionCard.
	 * 
	 * @param posArray
	 *            an integer, the slot of the PermissionCard that was bought
	 * @return the permission card which was bought
	 * @see Region
	 */
	public PermissionCard givePermissionCard(int posArray) {
		PermissionCard tempCard = permissionCards[posArray];
		generatePermissionCard(posArray);
		return tempCard;
	}

	/**
	 * Returns the list of the {@link City Cities} located in this Region.
	 * 
	 * @return the list of the Cities in this Region
	 * @see Region
	 */
	public List<City> getCities() {
		return cities;
	}

	/**
	 * Returns the {@link Council} located in this Region.
	 * 
	 * @return the Council of the Region
	 * @see Region
	 */
	public Council getCouncil() {
		return regionCouncil;
	}

	/**
	 * Searches for a {@link City} in the Cities list of this Region.
	 * 
	 * @param cityName
	 *            the name of the desired City
	 * @return the specific City; <code>null</code> if it doesn't exists
	 * @see Region
	 */
	public City getCity(String cityName) {
		for (City tempCity : cities) {
			if (tempCity.getName().equals(cityName))
				return tempCity;
		}
		return null;
	}

	/**
	 * Returns how many {@link PermissionCard PermissionCards} are uncovered on
	 * the table.
	 * 
	 * @return the number of uncovered PermissionCards of this Region
	 * @see Region
	 */
	public int getPermissionSlotsNumber() {
		return this.permissionCards.length;
	}
}
