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

/**
 * This is the class which loads the configuration from the xml file
 * 
 * @author Matteo Colombo
 *
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
	 * Calls the method which loads
	 * 
	 * @throws ConfigurationErrorException
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
		NodeList list = (NodeList) xpath.compile(COUNCILPATH + "councilorPerColor").evaluate(xmlDoc, XPathConstants.NODESET);
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
	 * 
	 * @return the initial coins of the first player
	 */
	public int getInitialPlayerMoney() {
		return initialPlayerMoney;
	}

	/**
	 * 
	 * @return the inital assistants of the first player
	 */
	public int getInitialPlayerHelpers() {
		return initialPlayerHelpers;
	}

	/**
	 * 
	 * @return the number of politic cards that every player has at the start of
	 *         the game
	 */
	public int getInitialPoliticCards() {
		return initialPoliticCards;
	}

	/**
	 * 
	 * @return the maximum number of emporiums that every player can build
	 */
	public int getInitialEmporiums() {
		return initialEmporiums;
	}

	/**
	 * 
	 * @return the default number of victory points of every player at the start
	 *         of the game
	 */
	public int getInitialVictoryPoints() {
		return initialVictoryPoints;
	}

	/**
	 * 
	 * @return the default amount of nobility points of every player at the
	 *         start of the game
	 */
	public int getInitialNobility() {
		return initialNobility;
	}

	/**
	 * 
	 * @return the maximum number of players per game on this server
	 */
	public int getMaxNumberOfPlayer() {
		return maxNumberOfPlayer;
	}

	/**
	 * Return the list of the colors used by the politc cars
	 * @return a list of Color
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Color> getColorsList() {
		return (List<Color>) ((ArrayList) colorsList).clone();
	}

	/**
	 * The map used to translate colors from English to hex, English names are the key, the Color objects are the elements
	 * @return a map of Colors
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Color> getColorsTranslation() {
		return (Map<String, Color>) ((HashMap) colorTranslation).clone();
	}

	/**
	 * 
	 * @return the number of councilors per color
	 */
	public int getCouncilorsPerColor() {
		return councilorsPerColor;
	}

	/**
	 * 
	 * @return return the number of councilors per council
	 */
	public int getCouncilSize() {
		return councilSize;
	}

	/**
	 * 
	 * @return the number of disclosed permission card per region
	 */
	public int getNumberDisclosedCards() {
		return numberDisclosedCards;
	}

	/**
	 * 
	 * @return the path to the nobility track file
	 */
	public String getNobility() {
		return nobility;
	}

	/**
	 * Return the list with the paths of the maps
	 * @return the list of the maps
	 */
	@SuppressWarnings("unchecked")
	public List<String> getMaps() {
		return (List<String>) ((ArrayList<String>) maps).clone();
	}

	/**
	 * 
	 * @return the port used by the RMI server
	 */
	public int getRmiPort() {
		return rmiPort;
	}

	/**
	 * 
	 * @return the port used by the socket server
	 */
	public int getSocketPort() {
		return socketPort;
	}

	/**
	 * 
	 * @return the amount of victory point per region
	 */
	public int getRewardPerRegion() {
		return rewardPerRegion;
	}

	/**
	 * return the map of the color rewards, the Color is the ket and the prize
	 * is the element
	 * 
	 * @return
	 */
	public Map<Color, Integer> getColorRewards() {
		return colorRewards;
	}

	/**
	 * Return the ordered list of the board rewards
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Integer> getBoardRewards() {
		return (List<Integer>) ((ArrayList) boardRewards).clone();
	}

	/**
	 * 
	 * @return the IP of the server
	 */
	public String getServerAddress() {
		return serverIp;
	}

	/**
	 * Return the map with the colors of the politic cards, the english name as
	 * element and the Color objects as key
	 * 
	 * @return the politic card colors
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Color, String> getColorsTranslationReverse() {
		return (Map<Color, String>) ((HashMap) colorTranslationReverse).clone();
	}

	/**
	 * Returns an hash map which contains the city colors, the english name as
	 * element and the Color objects as key
	 * 
	 * @return the city colors
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Color, String> getCityColor() {
		return (Map<Color, String>) ((HashMap) this.cityColor).clone();
	}

	/**
	 * 
	 * @return the maximum time to play a round
	 */
	public int getTimeout() {
		return this.timeout;
	}

}
