package game.board;

public class King {
	private City kingLocation;
	private Council kingCouncil;
	
	/**
	 * Initializes the king it receives the Council of the king and the city, which is the king's first location and also the capital
	 * @param kingCity a City which is the capital 
	 * @param kingCouncil a Council which is the initial king's council
	 */
	public King(City kingCity, Council kingCouncil) {
		this.kingCouncil = kingCouncil;
		this.kingLocation = kingCity;
	}

	/**
	 * Return the current location of the King
	 * @return a City 
	 */
	public City getKingLocation() {
		return kingLocation;
		
	}

	/**
	 * Moves the King from its current City to the desired one
	 * @param newKingCity a City in which you want to move the king
	 */
	public void moveKing(City newKingCity) {
		this.kingLocation = newKingCity;
	}
	
	/**
	 * Returns the King's council
	 * @return a Council
	 * @see Council
	 */
	public Council getKingCouncil() {
		return kingCouncil;
	}
}
