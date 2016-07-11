package server.model.configuration;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.model.Game;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.council.Council;
import server.model.board.council.Councilor;
import server.model.board.nobility.NobilityTrack;
import server.model.player.Assistants;
import server.model.player.Coins;
import server.model.player.Emporium;
import server.model.player.Nobility;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;
import server.model.player.VictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardReward;

/**
 * A class that loads the Configurations of the Game from the XML file.
 * 
 * @author Matteo Colombo
 * @see Game
 */
public class Configuration {
	private static final String CONFIGPATH = "src/main/resources/config.xml";
	private int initialPlayerMoney;
	private int initialPlayerHelpers;
	private int initialPoliticCards;
	private int initialEmporiums;
	private int initialVictoryPoints;
	private int initialNobility;
	private int maxNumberOfPlayer;

	private List<Color> colorsList;
	private Map<String, Color> colorTranslation;
	private Map<Color, String> colorTranslationReverse;

	private int councilorsPerColor;
	private int councilSize;

	private int numberDisclosedCards;
	private int rewardPerRegion;

	private String nobility;

	private List<String> maps;

	private int rmiPort;
	private int socketPort;
	private String serverIp;
	private int timeout;

	private Map<Color, String> cityColor;

	private Map<Color, Integer> colorRewards;

	private List<Integer> boardRewards;

	private static final String PLAYERPATH = "/config/player/";
	private static final String COLORPATH = "/config/colors/";
	private static final String COUNCILPATH = "/config/council/";
	private static final String REGIONPATH = "/config/region/";
	private static final String NOBILITYPATH = "/config/nobility/";
	private static final String MAPPATH = "/config/map/";
	private static final String SERVERPATH = "/config/server/";
	private static final String COLORREWARDPATH = "/config/city/reward/";
	private static final String BOARDPATH = "/config/board/value";
	private static final String CITYCOLORPATH = "/config/citycolor/color/";

	/**
	 * Loads all the Configurations from the XML file.
	 * 
	 * @throws ConfigurationErrorException
	 * @see Configuration
	 */
	public Configuration() throws ConfigurationErrorException {
		loadXMLFile();
	}

	private void loadXMLFile() throws ConfigurationErrorException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			File xmlFile = new File(CONFIGPATH);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlFile);
			XPath xpath = XPathFactory.newInstance().newXPath();
			loadPlayersConfig(xpath, xmlDoc);
			loadColors(xpath, xmlDoc);
			loadCouncil(xpath, xmlDoc);
			loadRegion(xpath, xmlDoc);
			loadNobility(xpath, xmlDoc);
			loadMap(xpath, xmlDoc);
			loadServer(xpath, xmlDoc);
			loadBoardRewards(xpath, xmlDoc);
			loadColorRewards(xpath, xmlDoc);
			loadCityColor(xpath, xmlDoc);
		} catch (ParserConfigurationException | XPathExpressionException | SAXException | IOException e) {
			throw new ConfigurationErrorException(e);
		}
	}

	private void loadPlayersConfig(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(PLAYERPATH + "money").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialPlayerMoney = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(PLAYERPATH + "helpers").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialPlayerHelpers = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(PLAYERPATH + "politic").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialPoliticCards = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(PLAYERPATH + "emporiums").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialEmporiums = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(PLAYERPATH + "victory").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialVictoryPoints = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(PLAYERPATH + "nobility").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialNobility = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(PLAYERPATH + "max").evaluate(xmlDoc, XPathConstants.NODESET);
		this.maxNumberOfPlayer = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	private void loadColors(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		colorsList = new ArrayList<>();
		colorTranslation = new HashMap<>();
		colorTranslationReverse = new HashMap<>();
		NodeList colors = (NodeList) xpath.compile(COLORPATH + "color/value").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < colors.getLength(); i++)
			colorsList.add(Color.decode(colors.item(i).getFirstChild().getNodeValue()));
		NodeList names = (NodeList) xpath.compile(COLORPATH + "color/name").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < colors.getLength(); i++) {
			colorTranslation.put(names.item(i).getFirstChild().getNodeValue(),
					Color.decode(colors.item(i).getFirstChild().getNodeValue()));
			colorTranslationReverse.put(Color.decode(colors.item(i).getFirstChild().getNodeValue()),
					names.item(i).getFirstChild().getNodeValue());
		}
	}

	private void loadCouncil(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(COUNCILPATH + "councilorPerColor").evaluate(xmlDoc,
				XPathConstants.NODESET);
		this.councilorsPerColor = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(COUNCILPATH + "size").evaluate(xmlDoc, XPathConstants.NODESET);
		this.councilSize = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	private void loadRegion(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(REGIONPATH + "disclosed").evaluate(xmlDoc, XPathConstants.NODESET);
		this.numberDisclosedCards = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(REGIONPATH + "award").evaluate(xmlDoc, XPathConstants.NODESET);
		this.rewardPerRegion = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	private void loadNobility(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(NOBILITYPATH + "path").evaluate(xmlDoc, XPathConstants.NODESET);
		this.nobility = list.item(0).getFirstChild().getNodeValue();
	}

	private void loadMap(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		maps = new ArrayList<>();
		NodeList list = (NodeList) xpath.compile(MAPPATH + "path").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < list.getLength(); i++)
			maps.add(list.item(i).getFirstChild().getNodeValue());
	}

	private void loadServer(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(SERVERPATH + "rmi").evaluate(xmlDoc, XPathConstants.NODESET);
		this.rmiPort = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(SERVERPATH + "socket").evaluate(xmlDoc, XPathConstants.NODESET);
		this.socketPort = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(SERVERPATH + "ip").evaluate(xmlDoc, XPathConstants.NODESET);
		this.serverIp = list.item(0).getFirstChild().getNodeValue();
		list = (NodeList) xpath.compile(SERVERPATH + "timeout").evaluate(xmlDoc, XPathConstants.NODESET);
		this.timeout = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	private void loadColorRewards(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		colorRewards = new HashMap<>();
		NodeList values = (NodeList) xpath.compile(COLORREWARDPATH + "value").evaluate(xmlDoc, XPathConstants.NODESET);
		NodeList colors = (NodeList) xpath.compile(COLORREWARDPATH + "color").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < values.getLength(); i++)
			colorRewards.put(Color.decode(colors.item(i).getFirstChild().getNodeValue()),
					Integer.parseInt(values.item(i).getFirstChild().getNodeValue()));
	}

	private void loadBoardRewards(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		boardRewards = new ArrayList<>();
		NodeList list = (NodeList) xpath.compile(BOARDPATH).evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < list.getLength(); i++)
			boardRewards.add(Integer.parseInt(list.item(i).getFirstChild().getNodeValue()));
	}

	private void loadCityColor(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		cityColor = new HashMap<>();
		NodeList values = (NodeList) xpath.compile(CITYCOLORPATH + "value").evaluate(xmlDoc, XPathConstants.NODESET);
		NodeList colors = (NodeList) xpath.compile(CITYCOLORPATH + "name").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < values.getLength(); i++)
			cityColor.put(Color.decode(values.item(i).getFirstChild().getNodeValue()),
					colors.item(i).getFirstChild().getNodeValue());
	}

	/**
	 * Returns the initial {@link Coins} of the first {@link Player}.
	 * 
	 * @return the initial Coins of the first Player
	 * @see Configuration
	 */
	public int getInitialPlayerMoney() {
		return initialPlayerMoney;
	}

	/**
	 * Returns the initial {@link Assistants} of the first {@link Player}.
	 * 
	 * @return the initial Assistants of the first Player
	 * @see Configuration
	 */
	public int getInitialPlayerHelpers() {
		return initialPlayerHelpers;
	}

	/**
	 * Returns the number of {@link PoliticCard PoliticCards} that every
	 * {@link Player} has at the start of the Game.
	 * 
	 * @return the number of PoliticCards that every {@link Player} has at the
	 *         start of the Game
	 * @see Configuration
	 */
	public int getInitialPoliticCards() {
		return initialPoliticCards;
	}

	/**
	 * Returns the maximum number of {@link Emporium Emporiums} that every
	 * {@link Player} can build.
	 * 
	 * @return the maximum number of Emporiums that every Player can build
	 * @see Configuration
	 */
	public int getInitialEmporiums() {
		return initialEmporiums;
	}

	/**
	 * Returns the default number of {@link VictoryPoints} of every
	 * {@link Player} at the start of the Game.
	 * 
	 * @return the default number of VictoryPoints of every Player at the start
	 *         of the Game
	 * @see Configuration
	 */
	public int getInitialVictoryPoints() {
		return initialVictoryPoints;
	}

	/**
	 * Returns the default amount of {@link Nobility} that every {@link Player}
	 * has at the start of the Game.
	 * 
	 * @return the default amount of Nobility that every Player has at the start
	 *         of the Game
	 * @see Configuration
	 */
	public int getInitialNobility() {
		return initialNobility;
	}

	/**
	 * Returns the maximum number of {@link Player Players} per Game on this
	 * Server.
	 * 
	 * @return the maximum number of Players per Game on this Server
	 * @see Configuration
	 */
	public int getMaxNumberOfPlayer() {
		return maxNumberOfPlayer;
	}

	/**
	 * Returns the list of the {@link Color Colors} used for the
	 * {@link PoliticCard PoliticCards}.
	 * 
	 * @return the list of the Colors used for the PoliticCards
	 * @see Configuration
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Color> getColorsList() {
		return (List<Color>) ((ArrayList) colorsList).clone();
	}

	/**
	 * The HashMap used to translate {@link Color Colors} from English to hex,
	 * where the English names are the keys and the Color objects are the
	 * elements.
	 * 
	 * @return the HashMap of Colors
	 * @see Configuration
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Color> getColorsTranslation() {
		return (Map<String, Color>) ((HashMap) colorTranslation).clone();
	}

	/**
	 * Returns the number of {@link Councilor Councilor} per {@link Color}.
	 * 
	 * @return the number of Councilors per Color
	 * @see Configuration
	 */
	public int getCouncilorsPerColor() {
		return councilorsPerColor;
	}

	/**
	 * Returns the number of {@link Councilor Councilor} per {@link Council}.
	 * 
	 * @return the number of Councilors per Council
	 * @see Configuration
	 */
	public int getCouncilSize() {
		return councilSize;
	}

	/**
	 * Returns the number of disclosed {@link PermissionCard PermissionCards}
	 * per {@link Region}.
	 * 
	 * @return the number of disclosed PermissionCards per Region
	 * @see Configuration
	 */
	public int getNumberDisclosedCards() {
		return numberDisclosedCards;
	}

	/**
	 * Returns the path of the {@link NobilityTrack} file.
	 * 
	 * @return the path of the NobilityTrack file
	 * @see Configuration
	 */
	public String getNobility() {
		return nobility;
	}

	/**
	 * Returns the list with the paths of the Maps.
	 * 
	 * @return the list with the paths of the Maps
	 * @see Configuration
	 */
	@SuppressWarnings("unchecked")
	public List<String> getMaps() {
		return (List<String>) ((ArrayList<String>) maps).clone();
	}

	/**
	 * Returns the port used by the RMI Server.
	 * 
	 * @return the port used by the RMI Server
	 * @see Configuration
	 */
	public int getRmiPort() {
		return rmiPort;
	}

	/**
	 * Returns the port used by the Socket Server.
	 * 
	 * @return the port used by the Socket Server
	 * @see Configuration
	 */
	public int getSocketPort() {
		return socketPort;
	}

	/**
	 * Returns the amount of {@link VictoryPoints} per {@link Region}.
	 * 
	 * @return the amount of VictoryPoints per Region
	 * @see Configuration
	 */
	public int getRewardPerRegion() {
		return rewardPerRegion;
	}

	/**
	 * Returns the HashMap of the {@link BoardColorReward BoardColorRewards},
	 * where the {@link Color} is the key and the prize is the element.
	 * 
	 * @return the HashMap of the BoardColorRewards
	 * @see Configuration
	 */
	public Map<Color, Integer> getColorRewards() {
		return colorRewards;
	}

	/**
	 * Returns the ordered list of the {@link BoardReward BoardRewards}.
	 * 
	 * @return the ordered list of the BoardRewards
	 * @see Configuration
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Integer> getBoardRewards() {
		return (List<Integer>) ((ArrayList) boardRewards).clone();
	}

	/**
	 * Returns the IP of the Server.
	 * 
	 * @return the IP of the server
	 * @see Configuration
	 */
	public String getServerAddress() {
		return serverIp;
	}

	/**
	 * Returns the HashMap with the {@link Color Colors} of the
	 * {@link PoliticCard PoliticCards}, where the English names are the
	 * elements and the Color objects are the keys.
	 * 
	 * @return the HashMap of the PoliticCards Colors
	 * @see Configuration
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Color, String> getColorsTranslationReverse() {
		return (Map<Color, String>) ((HashMap) colorTranslationReverse).clone();
	}

	/**
	 * Returns the HashMap that contains the {@link City Cities} {@link Color
	 * Colors}, where the English names are the elements and the Color objects
	 * are the keys.
	 * 
	 * @return the HashMap of the Cities Colors
	 * @see Configuration
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Color, String> getCityColor() {
		return (Map<Color, String>) ((HashMap) this.cityColor).clone();
	}

	/**
	 * Returns the maximum time to play a round.
	 * 
	 * @return the maximum time to play a round
	 * @see Configuration
	 */
	public int getTimeout() {
		return this.timeout;
	}

}
