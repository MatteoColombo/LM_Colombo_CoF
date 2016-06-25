package model;

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

import model.exceptions.ConfigurationErrorException;

public class Configuration {
	private static final String CONFIGPATH = "src/main/resources/config.xml";
	private int initialPlayerMoney;
	private int initialPlayerHelpers;
	private int initialPoliticCards;
	private int initialEmporiums;
	private int initialVictoryPoints;
	private int initialNobilityPoints;
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
	private static final String BOARDPATH = "/config/board/";

	public Configuration() throws ConfigurationErrorException {
		loadXMLFile();
	}

	public void loadXMLFile() throws ConfigurationErrorException {
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

		} catch (ParserConfigurationException pec) {
			throw new ConfigurationErrorException(pec);
		} catch (IOException ioe) {
			throw new ConfigurationErrorException(ioe);
		} catch (SAXException saxe) {
			throw new ConfigurationErrorException(saxe);
		} catch (XPathExpressionException xpee) {
			throw new ConfigurationErrorException(xpee);
		}
	}

	public void loadPlayersConfig(XPath xpath, Document xmlDoc) throws XPathExpressionException {
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
		this.initialNobilityPoints = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(PLAYERPATH + "max").evaluate(xmlDoc, XPathConstants.NODESET);
		this.maxNumberOfPlayer = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	public void loadColors(XPath xpath, Document xmlDoc) throws XPathExpressionException {
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

	public void loadCouncil(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(COUNCILPATH + "councilorPerColor").evaluate(xmlDoc,
				XPathConstants.NODESET);
		this.councilorsPerColor = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(COUNCILPATH + "size").evaluate(xmlDoc, XPathConstants.NODESET);
		this.councilSize = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	public void loadRegion(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(REGIONPATH + "disclosed").evaluate(xmlDoc, XPathConstants.NODESET);
		this.numberDisclosedCards = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(REGIONPATH + "award").evaluate(xmlDoc, XPathConstants.NODESET);
		this.rewardPerRegion = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	public void loadNobility(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(NOBILITYPATH + "path").evaluate(xmlDoc, XPathConstants.NODESET);
		this.nobility = list.item(0).getFirstChild().getNodeValue();
	}

	public void loadMap(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		maps = new ArrayList<>();
		NodeList list = (NodeList) xpath.compile(MAPPATH + "path").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < list.getLength(); i++)
			maps.add(list.item(i).getFirstChild().getNodeValue());
	}

	public void loadServer(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(SERVERPATH + "rmi").evaluate(xmlDoc, XPathConstants.NODESET);
		this.rmiPort = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(SERVERPATH + "socket").evaluate(xmlDoc, XPathConstants.NODESET);
		this.socketPort = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(SERVERPATH + "ip").evaluate(xmlDoc, XPathConstants.NODESET);
		this.serverIp = list.item(0).getFirstChild().getNodeValue();
	}

	public void loadColorRewards(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		colorRewards = new HashMap<>();
		NodeList values = (NodeList) xpath.compile(COLORREWARDPATH + "value").evaluate(xmlDoc, XPathConstants.NODESET);
		NodeList colors = (NodeList) xpath.compile(COLORREWARDPATH + "color").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < values.getLength(); i++)
			colorRewards.put(Color.decode(colors.item(i).getFirstChild().getNodeValue()),
					Integer.parseInt(values.item(i).getFirstChild().getNodeValue()));
	}

	public void loadBoardRewards(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		boardRewards = new ArrayList<>();
		NodeList list = (NodeList) xpath.compile(BOARDPATH + "value").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < list.getLength(); i++)
			boardRewards.add(Integer.parseInt(list.item(i).getFirstChild().getNodeValue()));
	}

	public int getInitialPlayerMoney() {
		return initialPlayerMoney;
	}

	public int getInitialPlayerHelpers() {
		return initialPlayerHelpers;
	}

	public int getInitialPoliticCards() {
		return initialPoliticCards;
	}

	public int getInitialEmporiums() {
		return initialEmporiums;
	}

	public int getInitialVictoryPoints() {
		return initialVictoryPoints;
	}

	public int getInitialNobilityPoints() {
		return initialNobilityPoints;
	}

	public int getMaxNumberOfPlayer() {
		return maxNumberOfPlayer;
	}

	public List<Color> getColorsList() {
		return colorsList;
	}

	public Map<String, Color> getColorsTranslation() {
		return colorTranslation;
	}

	public int getCouncilorsPerColor() {
		return councilorsPerColor;
	}

	public int getCouncilSize() {
		return councilSize;
	}

	public int getNumberDisclosedCards() {
		return numberDisclosedCards;
	}

	public String getNobility() {
		return nobility;
	}

	public List<String> getMaps() {
		return maps;
	}

	public int getRmiPort() {
		return rmiPort;
	}

	public int getSocketPort() {
		return socketPort;
	}

	public int getRewardPerRegion() {
		return rewardPerRegion;
	}

	public Map<Color, Integer> getColorRewards() {
		return colorRewards;
	}

	public List<Integer> getBoardRewards() {
		return boardRewards;
	}

	public String getServerAddress() {
		return serverIp;
	}

	public Map<Color, String> getColorsTranslationReverse() {
		return this.colorTranslationReverse;
	}
}
