package server.model.board.map;

import server.model.board.city.City;
import server.model.board.city.CityConnection;

/**
 * A class that represent the {@link #getDistance() distance} between a
 * {@link #getCity() City} (an intermediate or the terminal one) and the
 * beginning one.
 * 
 * @see City
 * @see CityConnection
 */
public class Distance {
	private City graphNode;
	private int distanceFromRoot;

	/**
	 * Initializes the class with the a {@link City} that is or an intermediate
	 * or the terminal and its distance from the root.
	 * 
	 * @param graphNode
	 *            the considered City
	 * @param distanceFromRoot
	 *            the distance of that City from its root
	 */
	public Distance(City graphNode, int distanceFromRoot) {
		this.distanceFromRoot = distanceFromRoot;
		this.graphNode = graphNode;
	}

	/**
	 * Returns the {@link City}.
	 *
	 * @return the considered City
	 * @see Distance
	 */
	public City getCity() {
		return graphNode;
	}

	/**
	 * Return the distance of that considered {@link City} from its root.
	 * 
	 * @return the distance of the City form its root
	 * @see Distance
	 */
	public int getDistance() {
		return distanceFromRoot;
	}
}
