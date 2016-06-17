package client.model;

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
	private static final String CONFIGPATH = "src/main/resources/client_config.xml";

	private List<Color> colorsList;
	private Map<String, Color> colorTranslation;

	private List<String> maps;

	private String serverIP;
	private int rmiPort;
	private int socketPort;

	private static final String COLORPATH = "/config/colors/";
	private static final String MAPPATH = "/config/map/";
	private static final String SERVERPATH = "/config/server/";

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
			loadColors(xpath, xmlDoc);
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

	public void loadColors(XPath xpath, Document xmlDoc) throws XPathExpressionException {
		colorsList = new ArrayList<>();
		colorTranslation = new HashMap<>();
		NodeList colors = (NodeList) xpath.compile(COLORPATH + "color/value").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < colors.getLength(); i++)
			colorsList.add(Color.decode((colors.item(i).getFirstChild().getNodeValue())));
		NodeList names = (NodeList) xpath.compile(COLORPATH + "color/name").evaluate(xmlDoc, XPathConstants.NODESET);
		for (int i = 0; i < colors.getLength(); i++)
			colorTranslation.put(names.item(i).getFirstChild().getNodeValue(),
					Color.decode(colors.item(i).getFirstChild().getNodeValue()));
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
		this.serverIP= list.item(0).getFirstChild().getNodeValue();		
	}

	public List<Color> getColorsList() {
		return colorsList;
	}

	public Map<String, Color> getColorsTranslation() {
		return colorTranslation;
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

	public String getServerIP() {
		return this.serverIP;
	}

}
