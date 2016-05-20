package game.board;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import game.board.city.City;
import game.board.city.CityConnection;
import game.exceptions.XMLFileException;
import game.reward.*;

public class MapLoader {
	private final String xmlPath;
	private List<Region> regions;
	private CouncilorPool pool;
	private List<CityConnection> connections;

	public MapLoader(String xmlPath, CouncilorPool pool) throws XMLFileException {
		this.xmlPath = xmlPath;
		this.pool = pool;
		this.regions = new ArrayList<>();
		loadXML();
		loadConnections();

	}

	
	private void loadConnections(){
		
	}
	
	/**
	 * It scans the XML file and extracts the regions, then calls the function which extracts the cities
	 * when a region is completed, it creates its object
	 * @throws XMLFileException
	 */
	public void loadXML() throws XMLFileException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		connections = new ArrayList<>();
		try {
			File xml = new File(xmlPath);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xml);
			xmlDoc.getDocumentElement().normalize();
			Element root = xmlDoc.getDocumentElement();
			NodeList regionsList = root.getElementsByTagName("region");
			for (int i = 0; i < regionsList.getLength(); i++) {
				Node region = regionsList.item(i);
				NodeList citiesOfRegion = region.getChildNodes();
				List<City> cities = parseCities(citiesOfRegion);
				regions.add(new Region("name", cities, pool.getCouncil(), 2));
			}
		} catch (ParserConfigurationException pec) {
			throw new XMLFileException(pec);
		} catch (IOException ioe) {
			throw new XMLFileException(ioe);
		} catch (SAXException saxe) {
			throw new XMLFileException(saxe);
		}

	}

	/**
	 * It receives the list of the cities of a region.
	 * It extracts the single regions and calls the function which generates the City objects
	 * @param citiesOfRegion a NodeList of the cities
	 * @return
	 */
	private List<City> parseCities(NodeList citiesOfRegion) {
		List<City> cities = new ArrayList<>();
		for (int j = 0; j < citiesOfRegion.getLength(); j++) {
			Node cityXML = citiesOfRegion.item(j);
			if (cityXML.getNodeType() == Node.ELEMENT_NODE) {
				cities.add(parseCityAttr(cityXML));

			}
		}
		return cities;
	}

	
	/**
	 * It receives a City. It extracts the parameters and then it creates the Object 
	 * @param cityXML the Node of the city
	 * @return a City object
	 */
	private City parseCityAttr(Node cityXML) {
		boolean isCapital = false;
		NodeList cityAttr = cityXML.getChildNodes();
		String name = "";
		String color = "";
		List<String> connectedCities = new ArrayList<>();
		for (int k = 0; k < cityAttr.getLength(); k++) {
			Node attr = cityAttr.item(k);
			String attrType = attr.getNodeName();
			switch (attrType) {
			case "name":
				name = attr.getTextContent();
				break;
			case "color":
				color = attr.getTextContent();
				break;
			case "connection":
				connectedCities.add(attr.getTextContent());
				break;
			case "capital":
				isCapital = Boolean.parseBoolean(attr.getTextContent());
				break;
			default:
				break;
			}
		}
		for (String temp : connectedCities)
			connections.add(new CityConnection(name, temp));
		City generatedCity;

		if (isCapital)
			generatedCity = new City(Color.decode(color), name, new Reward(Bonus.getAllStandardBonus(), 1, 40));
		else
			generatedCity = new City(Color.decode(color), name, new Reward(Bonus.getAllStandardBonus(), 1, 40),
					isCapital);
		return generatedCity;
	}

	/**
	 * Returns the list of the regions
	 * @return a list
	 */
	public List<Region> getRegions() {
		return regions;
	}

	/**
	 * Returns the capital
	 * @return a City
	 */
	public City getKingCity() {
		return null;
	}

	/**
	 * Returns the number of regions
	 * @return an integer
	 */
	public int getRegionsNumber() {
		return regions.size();
	}

	
}
