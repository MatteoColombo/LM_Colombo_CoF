package model.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.Configuration;
import model.board.city.City;
import model.board.council.Council;
import model.board.council.CouncilorPool;
import model.board.map.MapLoader;
import model.board.nobility.NobilityLoader;
import model.board.nobility.NobilityTrack;
import model.exceptions.MapXMLFileException;
import model.exceptions.TrackXMLFileException;
import model.exceptions.XMLFileException;
import model.reward.BVictoryPoints;
import model.reward.BoardColorReward;
import model.reward.BoardRegionReward;

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
	private Configuration config;
	private BoardRewardsManager boardRewManager;

	/**
	 * Constructor of the Board, it receives the configuration parameters and
	 * calls the initialization function
	 * 
	 * @param mapPath
	 *            a string, the path to the map's xml file
	 * @param nobleTrackSize
	 *            an integer, the length of the nobility track
	 * @param councPerColor
	 *            an integer, the number of councilor per color
	 * @param concSize
	 *            an integer, the number of councilor per council
	 * @param colors
	 *            a list of the politic cards and councilor colors
	 * @throws MapXMLFileException
	 */
	private Board(String mapPath, String nobilityPath, int councPerColor, int concSize, List<Color> colors)
			throws XMLFileException {
		this.mapPath = mapPath;
		this.nobilityPath = nobilityPath;
		this.concSize = concSize;
		this.colors = colors;
		this.councPerColor = councPerColor;
		
	}

	public Board(Configuration config, int choosenMap) throws XMLFileException {
		this(config.getMaps().get(choosenMap), config.getNobility(), config.getCouncilorsPerColor(),
				config.getCouncilSize(), config.getColorsList());
		this.config = config;
		initializeBoard();
	}

	/**
	 * Initializes the board with all the needed elements
	 * 
	 * @throws MapXMLFileException
	 */
	private void initializeBoard() throws XMLFileException {
		this.councilManager = new CouncilorPool(councPerColor, concSize, colors);
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
		this.regions = mapManager.getRegions();
		List<BoardColorReward> bColorRew= initializeColorReward();
		List<BoardRegionReward> bRegionRew = initializeRegionReward();
		List<BVictoryPoints> bKingRew = initializeKingReward();
		this.boardRewManager= new BoardRewardsManager(bColorRew, bRegionRew, bKingRew);
	}
	
	public List<BoardColorReward> initializeColorReward(){
		List<BoardColorReward> bColorRew = new ArrayList<>();
		Set<Color> cityColors= config.getColorRewards().keySet();
		for(Color c: cityColors)
			bColorRew.add(new BoardColorReward(c, config.getColorRewards().get(c)));
		return bColorRew;
	}
	
	public List<BoardRegionReward> initializeRegionReward(){
		List<BoardRegionReward> bRegionRew= new ArrayList<>();
		for(int i=0; i< regions.size();i++)
			bRegionRew.add(new  BoardRegionReward(regions.get(i), config.getRewardPerRegion()));
		return bRegionRew;
	}
	
	public List<BVictoryPoints> initializeKingReward(){
		List<BVictoryPoints> bKingRew= new ArrayList<>();
		List<Integer> kingRewValues= config.getBoardRewards();
		for(int value: kingRewValues)
			bKingRew.add(new BVictoryPoints(value));
		return bKingRew;
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

	public King getKing() {
		return this.mapKing;
	}

	public BoardRewardsManager getBoardRewardsManager() {
		return this.bRewardsManager;
	}

	public void setBoardRewardsManager(BoardRewardsManager bRewardsManager) {
		this.bRewardsManager = bRewardsManager;
	}

	public CouncilorPool getCouncilorPool() {
		return this.councilManager;
	}

	public MapLoader getMap() {
		return this.mapManager;
	}

	public List<Region> getRegions() {
		return this.regions;
	}
	
	public BoardRewardsManager getBoardRewardManager(){
		return this.boardRewManager;
	}
}
