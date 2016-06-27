/**
 * 
 */
package server.control.dialogue.notify;

import client.control.ViewInterface;

/**
 * @author Matteo Colombo
 *
 */
public class NotifyBonusFromCities implements Notify {

	private static final long serialVersionUID = -1524841738697308094L;
	private int cities;
		
	public NotifyBonusFromCities(int cities) {
		this.cities=cities;
	}
	@Override
	public void execute(ViewInterface view) {
		view.changeStatusToNobilityBonus("You can choose "+cities+ " bonus from the cities that you own!","city");
	}
  
}
