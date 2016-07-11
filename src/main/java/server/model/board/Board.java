package server.model.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import server.model.Game;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.board.council.Councilor;
import server.model.board.council.CouncilorPool;
import server.model.board.map.MapLoader;
import server.model.board.map.MapXMLFileException;
import server.model.board.nobility.NobilityLoader;
import server.model.board.nobility.NobilityTrack;
import server.model.board.nobility.TrackXMLFileException;
import server.model.configuration.Configuration;
import server.model.configuration.XMLFileException;
import server.model.player.Player;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;
import server.model.reward.BoardReward;

/**
 * A class that represent the Board of the Game.
 * <p>
 * The Board loads the Configurations that will be used for the current Game, as
 * well as contains one of the possible {@link #getMap() Maps}, whose parameters
 * are loaded from a XML file, and nearly all the things each Player can
 * interact with, such as:
 * <ul>
 * <li>The {@link #getRegions() Regions}, {@link #getRegion(int) each of which}
 * has {@link #getRegionsNumber() its own identifier}, and their Cities;</li>
 * <li>The {@link #getKing() King}, that {@link #moveKing(City) can be moved}
 * form a {@link #getKingLocation() City where it was} to a new one;</li>
 * <li>The Councils, both the {@link #getKingCouncil() King} and the
 * {@link #getRegionCouncil(int) Regions} ones, and their
 * {@link #getCouncilorPool() Councilors};</li>
 * </ul>
 * On the Board there are also the {@link #getNobleTrack() NobilityTrack} and
 * all the {@link #getBoardRewardsManager() BoardRewards}, that are the
 * {@link #initializeColorReward() Color}, the {@link #initializeRegionReward()
 * Region} and the {@link #initializeKingReward() King's} ones, that are
 * {@link #setBoardRewardsManager(BoardRewardsManager) available for this Game}.
 * 
 * @see BoardReward
 * @see BoardRewardsManager
 * @see City
 * @see Color
 * @see Configuration
 * @see Council
 * @see Councilor
 * @see Game
 * @see King
 * @see MapLoader
 * @see NobilityTrack
 * @see Player
 * @see Region
 */
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
	private Configuration config;
	private BoardRewardsManager boardRewManager;
	private boolean randomConfig;

	/**
	 * Starts the initializations of the Board loading this {@link Configuration
	 * Configurations} and the chosen Map.
	 * 
	 * @param config
	 *            the chosen Configuration for this Game
	 * @param choosenMap
	 *            the chosen Map for this Game
	 * @throws XMLFileException
	 * @see Board
	 */
	public Board(Configuration config, int choosenMap, boolean randomConfig) throws XMLFileException {
		this(config.getMaps().get(choosenMap), config.getNobility(), config.getCouncilorsPerColor(),
				config.getCouncilSize(), config.getColorsList());
		this.config = config;
		this.randomConfig = randomConfig;
		initializeBoard();
		if (randomConfig)
			this.mapManager.generateConnections();
	}

	/**
	 * Initializes the Board receiving all the {@link Configuration} parameters.
	 * 
	 * @param mapPath
	 *            the path of the XML file of this Map
	 * @param nobleTrackSize
	 *            the length of the NobilityTrack
	 * @param councPerColor
	 *            the number of Councilors per Color
	 * @param concSize
	 *            the number of Councilors per Council
	 * @param colors
	 *            the list of the PoliticCards and Councilors Colors
	 * @throws MapXMLFileException
	 * @see Board
	 */
	private Board(String mapPath, String nobilityPath, int councPerColor, int concSize, List<Color> colors)
			throws XMLFileException {
		this.mapPath = mapPath;
		this.nobilityPath = nobilityPath;
		this.concSize = concSize;
		this.colors = colors;
		this.councPerColor = councPerColor;
	}

	/**
	 * Initializes the Board with all the needed elements.
	 * 
	 * @throws MapXMLFileException
	 * @see Board
	 */
	private void initializeBoard() throws XMLFileException {
		this.councilManager = new CouncilorPool(councPerColor, concSize, colors);
		NobilityLoader nl;
		try {
			this.mapManager = new MapLoader(mapPath, councilManager);
			if (!randomConfig)
				this.mapManager.loadConnections();
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
		List<BoardColorReward> bColorRew = initializeColorReward();
		List<BoardRegionReward> bRegionRew = initializeRegionReward();
		List<BVictoryPoints> bKingRew = initializeKingReward();
		this.boardRewManager = new BoardRewardsManager(bColorRew, bRegionRew, bKingRew);
	}

	/**
	 * Initializes the {@link BoardColorReward BoardColorRewards} list.
	 * 
	 * @return the BoardColorRewards list
	 * @see Board
	 */
	public List<BoardColorReward> initializeColorReward() {
		List<BoardColorReward> bColorRew = new ArrayList<>();
		Set<Color> cityColors = config.getColorRewards().keySet();
		for (Color c : cityColors)
			bColorRew.add(new BoardColorReward(c, config.getColorRewards().get(c)));
		return bColorRew;
	}

	/**
	 * Initializes the {@link BoardRegionReward BoardRegionRewards} list.
	 * 
	 * @return the BoardRegionRewards list
	 * @see Board
	 */
	public List<BoardRegionReward> initializeRegionReward() {
		List<BoardRegionReward> bRegionRew = new ArrayList<>();
		for (int i = 0; i < regions.size(); i++)
			bRegionRew.add(new BoardRegionReward(regions.get(i), config.getRewardPerRegion()));
		return bRegionRew;
	}

	/**
	 * Initializes the {@link BVictoryPoints BoardKingRewards} list.
	 * 
	 * @return the BoardKingRewards list
	 * @see Board
	 */
	public List<BVictoryPoints> initializeKingReward() {
		List<BVictoryPoints> bKingRew = new ArrayList<>();
		List<Integer> kingRewValues = config.getBoardRewards();
		for (int value : kingRewValues)
			bKingRew.add(new BVictoryPoints(value));
		return bKingRew;
	}

	/**
	 * Returns the number of the {@link Region Regions}.
	 * 
	 * @return the number of the Regions
	 * @see Board
	 */
	public int getRegionsNumber() {
		return this.regions.size();
	}

	/**
	 * Returns the specified {@link Region}.
	 * 
	 * @param posArray
	 *            the identifier of this Region
	 * @return the searched Region
	 * @see Board
	 */
	public Region getRegion(int posArray) {
		return regions.get(posArray);
	}

	/**
	 * Returns the {@link Council} of this specified {@link Region}
	 * 
	 * @param posArray
	 *            the identifier of this Region
	 * @return the Council of the searched Region
	 * @see Board
	 */
	public Council getRegionCouncil(int posArray) {
		return regions.get(posArray).getCouncil();
	}

	/**
	 * Returns the {@link NobilityTrack}.
	 * 
	 * @return the NobilityTrack
	 * @see Board
	 */
	public NobilityTrack getNobleTrack() {
		return this.track;
	}

	/**
	 * Moves the {@link King} from its current {@link City} to another one.
	 * 
	 * @param newKingCity
	 *            the City in which the King will be moved
	 * @see Board
	 */
	public void moveKing(City newKingCity) {
		this.mapKing.moveKing(newKingCity);
	}

	/**
	 * Returns the {@link City} in which the {@link King} is.
	 * 
	 * @return the current location of the King
	 * @see Board
	 */
	public City getKingLocation() {
		return this.mapKing.getKingLocation();
	}

	/**
	 * Returns the {@link King King's} {@link Council}.
	 * 
	 * @return the King's Council
	 * @see Board
	 */
	public Council getKingCouncil() {
		return this.mapKing.getKingCouncil();
	}

	/**
	 * Returns the {@link King} of the Board.
	 * 
	 * @return the King
	 * @see Board
	 */
	public King getKing() {
		return this.mapKing;
	}

	/**
	 * Returns the {@link BoardRewardsManager}.
	 * 
	 * @return the BoardRewardsManager
	 * @see Board
	 */
	public BoardRewardsManager getBoardRewardsManager() {
		return this.boardRewManager;
	}

	/**
	 * Returns the {@link CouncilorPool}.
	 * 
	 * @return the CouncilorPool
	 * @see Board
	 */
	public CouncilorPool getCouncilorPool() {
		return this.councilManager;
	}

	/**
	 * Returns the Map.
	 * 
	 * @return the Map
	 * @see Board
	 */
	public MapLoader getMap() {
		return this.mapManager;
	}

	/**
	 * Returns all the Board {@link Region Regions}.
	 * 
	 * @return the Board Regions
	 * @see Board
	 */
	public List<Region> getRegions() {
		return this.regions;
	}

}
