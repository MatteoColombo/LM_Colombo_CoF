package game.board;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import game.reward.*;

public class MapLoader {
	private final String xmlPath;
	private List<Region> regions;
	private CouncilorPool pool;
	private List<CityConnection> connections;

	public MapLoader(String xmlPath, CouncilorPool pool)
			throws SAXException, IOException, ParserConfigurationException {
		this.xmlPath = xmlPath;
		this.pool = pool;
		this.regions = new ArrayList<>();
		loadXML();

	}

	public void loadXML() throws SAXException, IOException, ParserConfigurationException {
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
			throw pec;
		} catch (IOException ioe) {
			throw ioe;
		} catch (SAXException saxe) {
			throw saxe;
		}

	}

	private List<City> parseCities(NodeList citiesOfRegion) {
		List<City> cities = new ArrayList<>();
		for (int j = 0; j < citiesOfRegion.getLength(); j++) {
			Node city = citiesOfRegion.item(j);
			if (city.getNodeType() == Node.ELEMENT_NODE) {
				boolean isCapital = Boolean.parseBoolean(city.getAttributes().getNamedItem("capital").getNodeValue());
				NodeList cityattr = city.getChildNodes();
				String name = "";
				String color = "";
				List<String> connectedCities = new ArrayList<>();
				for (int k = 0; k < cityattr.getLength(); k++) {
					Node attr = cityattr.item(k);
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
					default:
						break;
					}
				}
				for (String temp : connectedCities)
					connections.add(new CityConnection(name, temp));
				if (isCapital)
					cities.add(new City(Color.decode(color), name, new Reward(Bonus.getAllStandardBonus(), 1, 40)));
				else
					cities.add(new City(Color.decode(color), name, new Reward(Bonus.getAllStandardBonus(), 1, 40),
							isCapital));
			}
		}
		return cities;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public City getKingCity() {
		return null;
	}

	public int getRegionsNumber() {
		return regions.size();
	}

	
}
