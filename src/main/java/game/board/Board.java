package game.board;

import java.awt.Color;
import java.util.List;

import game.board.city.City;
import game.board.council.Council;
import game.exceptions.XMLFileException;

public class Board {
	private final String xmlPath;
	private final int councPerColor;
	private final int concSize;
	private final int nobleTrackSize;
	private final List<Color> colors;
	private List<Region> regions;
	private MapLoader xmlManager;
	private NobleTrack track;
	private King mapKing;
	private CouncilorPool councilManager;

	/**
	 * Constructor of the Board, it receives the configuration parameters and calls the initialization function
	 * @param xmlPath a string, the path to the map's xml file
	 * @param nobleTrackSize an integer, the lenght of the nobility track
	 * @param councPerColor an integer, the number of councilor per color
	 * @param concSize an integer, the number of councilor per council
	 * @param colors a list of the politic cards and councilor colors
	 * @throws XMLFileException
	 */
	public Board(String xmlPath, int nobleTrackSize, int councPerColor, int concSize, List<Color> colors)
			throws XMLFileException {
		this.xmlPath = xmlPath;
		this.concSize = concSize;
		this.colors = colors;
		this.councPerColor = councPerColor;
		this.nobleTrackSize = nobleTrackSize;
		initializeBoard();
	}

	/**
	 * Initializes the board with all the needed elements
	 * 
	 * @throws XMLFileException
	 */
	private void initializeBoard() throws XMLFileException {
		this.mapKing = new King(xmlManager.getKingCity(), councilManager.getCouncil());
		this.xmlManager = new MapLoader(xmlPath, councilManager);
		this.track = new NobleTrack(nobleTrackSize);
		this.councilManager = new CouncilorPool(councPerColor, concSize, colors);
		this.regions = xmlManager.getRegions();

	}

	/**
	 * Returns the number of the of the regions
	 * 
	 * @return an integer
	 */
	public int getRegionsNumber() {
		return this.regions.size();
	}

	/**
	 * Returns the specified region
	 * 
	 * @param posArray
	 *            an integer, the specified region
	 * @return a region
	 */
	public Region getRegion(int posArray) {
		return regions.get(posArray);
	}

	/**
	 * Returns the council of the specified region
	 * 
	 * @param posArray
	 *            an integer, the region
	 * @return a Council
	 */
	public Council getRegionCouncil(int posArray) {
		return regions.get(posArray).getCouncil();
	}

	/**
	 * Returns the noble track
	 * 
	 * @return a Nobletrack
	 */
	public NobleTrack getNobleTrack() {
		return this.track;
	}

	/**
	 * Moves the king from its current location to another city
	 * 
	 * @param newKingCity
	 *            the city in which the king has to be moved
	 */
	public void moveKing(City newKingCity) {
		this.mapKing.moveKing(newKingCity);
	}

	/**
	 * Returns the city in which the king is
	 * 
	 * @return a City
	 */
	public City getKingLocation() {
		return this.mapKing.getKingLocation();
	}

	/**
	 * Returns the king's council
	 * 
	 * @return a Council
	 */
	public Council getKingCouncil() {
		return this.mapKing.getKingCouncil();
	}

}
