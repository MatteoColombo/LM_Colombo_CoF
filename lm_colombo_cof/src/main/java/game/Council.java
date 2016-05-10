package game;

public class Council {
	final int COUNCIL_DIM = 4;
	private Councilor[] councMembers;
	private int first;

	public Council(Councilor[] councMembers) {
		this.councMembers = councMembers;
		this.first = COUNCIL_DIM - 1;
	}

	public void insertCounciler(Councilor councilor) {
		councMembers[first] = councilor;
		first = (first + 1) % COUNCIL_DIM;
	}

	public Councilor getCouncilor(int pos) {
		return councMembers[(first + pos) % COUNCIL_DIM];
	}
}
