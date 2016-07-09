package server.control.dialogue.request;

import client.control.ViewInterface;

/**
 * Notify to send when a player obtain the special nobility bonus 
 * that gives him an extra Reward from cities where he has an emporium
 */
public class RequestCity implements Request {

	private static final long serialVersionUID = -9037886782345936796L;

	private int cities;
	
	/**
	 * Create a new NotifyBonusFromCities
	 * @param cities how many city Rewards you can get
	 */
	public RequestCity(int cities) {
		this.cities=cities;
	}
	@Override
	public void execute(ViewInterface view) {
		view.changeStatusToNobilityBonus("You can choose "+cities+ " bonus from the cities that you own!","city");
	}

}
