package game.board;

import java.util.List;

public class Council {
	private List<Councilor> councMembers;

	public Council(List<Councilor> councMembers) {
		this.councMembers = councMembers;
	}

	public void insertCounciler(Councilor councilor) {
		councMembers.remove(0);
		councMembers.add(councilor);
	}
	

	public Councilor getCouncilor(int pos) {
		return councMembers.get(pos);
	}
}
