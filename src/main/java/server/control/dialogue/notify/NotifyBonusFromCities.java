/**
 * 
 */
package server.control.dialogue.notify;

import client.control.ViewInterface;

/**
 * Notify to send when a player obtain the special nobility bonus 
 * that gives him an extra Reward from cities where he has an emporium
 * @author Matteo Colombo
 */
public class NotifyBonusFromCities implements Notify {

	private static final long serialVersionUID = -1524841738697308094L;
	private int cities;
		
	/**
	 * Create a new NotifyBonusFromCities
	 * @param cities how many city Rewards you can get
	 */
	public NotifyBonusFromCities(int cities) {
		this.cities=cities;
	}
	@Override
	public void execute(ViewInterface view) {
		view.changeStatusToNobilityBonus("You can choose "+cities+ " bonus from the cities that you own!","city");
	}
  
}
