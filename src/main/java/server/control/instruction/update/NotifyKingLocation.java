package server.control.instruction.update;

import client.model.ModelInterface;

/**
 * This is the update which is sent to update the king's location
 * @author Matteo Colombo
 *
 */
public class NotifyKingLocation implements Update {

	private static final long serialVersionUID = 2653404705987899118L;
	private String location;
	
	/**
	 * Sets the city in which the king is located,  
	 * @param location the name of the city
	 */
	public NotifyKingLocation(String location) {
		this.location=location;
	}
	@Override
	public void execute(ModelInterface model) {
		model.setKingLocation(location);
	}

}
