package game.board;

import java.util.List;
/**
 * 
 * @author Matteo Colombo
 *
 */

public class Council {
	private List<Councilor> councMembers;

	/**
	 * 
	 * @param councMember a list of Councilors
	 * 
	 */
	public Council(List<Councilor> councMembers) {
		this.councMembers = councMembers;
	}

	/**
	 * 
	 * @param councilor a Councilor.
	 * it works like a queue, it removes the first position and adds the one which receives in tail
	 * 
	 */
	public void insertCounciler(Councilor councilor) {
		councMembers.remove(0);
		councMembers.add(councilor);
	}
	
	/**
	 * 
	 * @param pos the position of the desidered councilor
	 * @return the councilor in the specified position
	 */

	public Councilor getCouncilor(int pos) {
		return councMembers.get(pos);
	}
}
