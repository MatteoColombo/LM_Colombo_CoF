package server.model.market;

import java.io.Serializable;
import server.model.configuration.Configuration;

/**
 * This is the public interface that the items which can be sold in the market need to implement
 * @author Matteo Colombo
 *
 */
public interface Soldable extends Serializable {
	
	/**
	 * This method is used when you need to print a string with information about the objext
	 * @param config the configuration, it is needed to translate colors into text
	 * @return a string
	 */
	public String getMarketMessage(Configuration config);
	
	/**
	 * Every soldable object need to implement this method which is used to return a new copy of the object
	 * @return
	 */
	public Soldable newCopy();
}
