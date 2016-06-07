package model;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private static final String configPath = "src/main/resources/config.xml";
	private int initialPlayerMoney;
	private int initialPlayerHelpers;
	private int initialPoliticCards;
	private int initialEmporiums;
	private int initialVictoryPoints;
	private int initialNobilityPoints;
	private int maxNumberOfPlayer;

	private List<Color> colorsList;

	private int councilorsPerColor;
	private int councilSize;

	private int numberDisclosedCards;
	private int rewardPerRegion;

	private String nobility;

	private List<String> maps;

	private int rmiPort;
	private int socketPort;
	
	private List<Integer> colorRewards;
	
	private List<Integer> boardRewards;
	
	private final String playerPath = "/config/player/";
	private final String colorPath = "/config/colors/";
	private final String councilPath = "/config/council/";
	private final String regionPath = "/config/region/";
	private final String nobilityPath = "/config/nobility/";
	private final String mapPath = "/config/map/";
	private final String serverPath = "/config/server/";
	private final String colRewPath = "/config/city/";
	private final String boardPath = "/config/board/";
	

	public Configuration() throws ConfigurationErrorException {
		loadXMLFile();
	}

	public void loadXMLFile() throws ConfigurationErrorException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			File xmlFile = new File(configPath);
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
		NodeList list = (NodeList) xpath.compile(playerPath + "money").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialPlayerMoney = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(playerPath + "helpers").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialPlayerHelpers = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(playerPath + "politic").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialPoliticCards = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(playerPath + "emporiums").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialEmporiums = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(playerPath + "victory").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialVictoryPoints = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(playerPath + "nobility").evaluate(xmlDoc, XPathConstants.NODESET);
		this.initialNobilityPoints = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(playerPath + "max").evaluate(xmlDoc, XPathConstants.NODESET);
		this.maxNumberOfPlayer = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	public void loadColors(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		colorsList = new ArrayList<>();
		NodeList list = (NodeList) xpath.compile(colorPath + "color").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < list.getLength(); i++)
			colorsList.add(Color.decode(list.item(i).getFirstChild().getNodeValue()));

	}

	public void loadCouncil(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(councilPath + "councilorPerColor").evaluate(xmlDoc,
				XPathConstants.NODESET);
		this.councilorsPerColor = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(councilPath + "size").evaluate(xmlDoc, XPathConstants.NODESET);
		this.councilSize = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	public void loadRegion(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(regionPath + "disclosed").evaluate(xmlDoc, XPathConstants.NODESET);
		this.numberDisclosedCards = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(regionPath + "award").evaluate(xmlDoc, XPathConstants.NODESET);
		this.rewardPerRegion = Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	public void loadNobility(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(nobilityPath + "path").evaluate(xmlDoc, XPathConstants.NODESET);
		this.nobility = list.item(0).getFirstChild().getNodeValue();
	}

	public void loadMap(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		maps = new ArrayList<>();
		NodeList list = (NodeList) xpath.compile(mapPath + "path").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < list.getLength(); i++)
			maps.add(list.item(i).getFirstChild().getNodeValue());
	}

	public void loadServer(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		NodeList list = (NodeList) xpath.compile(serverPath + "rmi").evaluate(xmlDoc, XPathConstants.NODESET);
		this.rmiPort= Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
		list = (NodeList) xpath.compile(serverPath + "socket").evaluate(xmlDoc, XPathConstants.NODESET);
		this.socketPort= Integer.parseInt(list.item(0).getFirstChild().getNodeValue());
	}

	public void loadColorRewards(XPath xpath, Document xmlDoc) throws XPathExpressionException{
		colorRewards = new ArrayList<>();
		NodeList list = (NodeList) xpath.compile(colRewPath + "value").evaluate(xmlDoc, XPathConstants.NODESET);
		for(int i=0;i < list.getLength();i++)
			colorRewards.add(Integer.parseInt(list.item(i).getFirstChild().getNodeValue()));
	}
	
	public void loadBoardRewards(XPath xpath, Document xmlDoc) throws XPathExpressionException{
		boardRewards = new ArrayList<>();
		NodeList list = (NodeList) xpath.compile(boardPath + "value").evaluate(xmlDoc, XPathConstants.NODESET);
		for(int i=0;i < list.getLength();i++)
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

	public List<Integer> getColorRewards() {
		return colorRewards;
	}

	public List<Integer> getBoardRewards() {
		return boardRewards;
	}
}
