package server.model.market;

import java.io.Serializable;
import server.model.configuration.Configuration;

/**
 * An interface that have to be implemented by all the items that can be sold
 * in the Market.
 * 
 * @author Matteo Colombo
 * @see Market
 */
public interface Soldable extends Serializable {

	/**
	 * Prints a string with information about this object.
	 * 
	 * @param config
	 *            the Configuration; it is needed to translate the Colors into
	 *            text
	 * @return an information string of this object
	 * @see Soldable
	 */
	public String getMarketMessage(Configuration config);

	/**
	 * Return a new copy of this object.
	 * 
	 * @return a new copy of this object
	 * @see Soldable
	 */
	public Soldable newCopy();
}
