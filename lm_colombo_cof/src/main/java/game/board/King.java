package game.board;

public class King {
	private City kingLocation;
	private Council kingCouncil;

	public King(City kingCity, Council kingCouncil) {
		this.kingCouncil = kingCouncil;
		this.kingLocation = kingCity;
	}

	public City getKingLocation() {
		return kingLocation;
	}

	public void moveKing(City newKingCity) {
		this.kingLocation = newKingCity;
	}

	public Council getKingCouncil() {
		return kingCouncil;
	}
}
