package model.board;

import java.awt.Color;
import java.util.List;

import model.board.city.City;
import model.board.council.Council;
import model.board.council.CouncilorPool;
import model.board.map.MapLoader;
import model.board.nobility.NobilityLoader;
import model.board.nobility.NobilityTrack;
import model.exceptions.MapXMLFileException;
import model.exceptions.TrackXMLFileException;
import model.exceptions.XMLFileException;

public class Board {
	private final String mapPath;
	private final String nobilityPath;
	private final int councPerColor;
	private final int concSize;
	private final List<Color> colors;
	private List<Region> regions;
	private MapLoader mapManager;
	private NobilityTrack track;
	private King mapKing;
	private CouncilorPool councilManager;
	private BoardRewardsManager bRewardsManager;

	/**
	 * Constructor of the Board, it receives the configuration parameters and
	 * calls the initialization function
	 * 
	 * @param mapPath
	 *            a string, the path to the map's xml file
	 * @param nobleTrackSize
	 *            an integer, the lenght of the nobility track
	 * @param councPerColor
	 *            an integer, the number of councilor per color
	 * @param concSize
	 *            an integer, the number of councilor per council
	 * @param colors
	 *            a list of the politic cards and councilor colors
	 * @throws MapXMLFileException
	 */
	public Board(String mapPath, String nobilityPath, int councPerColor, int concSize, List<Color> colors)
			throws XMLFileException {
		this.mapPath = mapPath;
		this.nobilityPath = nobilityPath;
		this.concSize = concSize;
		this.colors = colors;
		this.councPerColor = councPerColor;
		initializeBoard();
	}

	/**
	 * Initializes the board with all the needed elements
	 * 
	 * @throws MapXMLFileException
	 */
	private void initializeBoard() throws XMLFileException {
		NobilityLoader nl;
		try {
			this.mapManager = new MapLoader(mapPath, councilManager);
		} catch (MapXMLFileException mxfe) {
			throw new XMLFileException(mxfe);
		}
		try {
			nl = new NobilityLoader(nobilityPath);
		} catch (TrackXMLFileException txfe) {
			throw new XMLFileException(txfe);
		}
		this.track = new NobilityTrack(nl.getNobilityTrack());
		this.mapKing = new King(mapManager.getKingCity(), councilManager.getCouncil());
		this.councilManager = new CouncilorPool(councPerColor, concSize, colors);
		this.regions = mapManager.getRegions();

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
	public NobilityTrack getNobleTrack() {
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

	public BoardRewardsManager getBoardRewardsManager() {
		return this.bRewardsManager;
	}

	public void setBoardRewardsManager(BoardRewardsManager bRewardsManager) {
		this.bRewardsManager = bRewardsManager;
	}

	// TODO check this out if necessary
	public City getCity(String s) {
		return this.mapManager.getCity(s);
	}
	
	public CouncilorPool getCouncilorPool() {
		return this.councilManager;
	}
}
