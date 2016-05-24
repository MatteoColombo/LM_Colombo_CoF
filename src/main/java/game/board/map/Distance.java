package game.board.map;

import game.board.city.City;

public class Distance {
	private City graphNode;
	private int distanceFromRoot;

	public Distance(City graphNode, int distanceFromRoot) {
		this.distanceFromRoot = distanceFromRoot;
		this.graphNode = graphNode;
	}

	/**
	 *
	 * @return the city
	 */
	public City getCity() {
		return graphNode;
	}

	/**
	 * Return the distance
	 * 
	 * @return
	 */
	public int getDistance() {
		return distanceFromRoot;
	}
}
