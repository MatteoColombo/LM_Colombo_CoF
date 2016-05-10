package game;

public class Council {
	final int COUNCIL_DIM = 4;
	private Councilor[] council;
	private int first;
	private int last;

	public Council(Councilor[] council) {
		this.council = council;
		this.first = COUNCIL_DIM - 1;
		this.last = 0;
	}

	public void insertCounciler(Councilor councilor) {
		council[first] = councilor;
		last = first;
		first = (first + 1) % COUNCIL_DIM;
	}

	public Councilor getCouncilor(int pos) {
		return council[(first + pos) % COUNCIL_DIM];
	}
}
