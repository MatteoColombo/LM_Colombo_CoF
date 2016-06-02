package model;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.exceptions.ConfigurationErrorException;
import model.exceptions.MapXMLFileException;

public class Config {
	private static final String configPath = "src/main/resources/config.xml";
	private int initialPlayerMoney;
	private int initialPlayerHelpers;
	private int initialPoliticCards;
	private int initialEmporiums;
	private int initialVictoryPoints;
	private int initialNobilityPoints;
	private int maxNumberOfPlayer;

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;

	public Config() throws ConfigurationErrorException{
		Element root = loadXMLFile();
		loadPlayerConfig(root);
	}

	public Element loadXMLFile() throws ConfigurationErrorException {
		factory = DocumentBuilderFactory.newInstance();
		try {
			File xmlFile = new File(configPath);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xmlFile);
			xmlDoc.getDocumentElement().normalize();
			Element root = xmlDoc.getDocumentElement();
			return root;
		} catch (ParserConfigurationException pec) {
			throw new ConfigurationErrorException(pec);
		} catch (IOException ioe) {
			throw new ConfigurationErrorException(ioe);
		} catch (SAXException saxe) {
			throw new ConfigurationErrorException(saxe);
		}
	}

	public void loadPlayerConfig(Element root) throws ConfigurationErrorException {
		NodeList player= root.getElementsByTagName("player");
		if(player.getLength()>1)
			throw new ConfigurationErrorException("There is a duplicate in the configuration file");
		NodeList playerAttributes= player.item(0).getChildNodes();
		for(int i=0; i< playerAttributes.getLength();i++)
			if(player.item(i).getNodeType()== Node.ELEMENT_NODE)
				return;
				
	}
}
