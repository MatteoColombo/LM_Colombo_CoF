package server.model.board.map;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import server.model.board.King;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.board.city.CityConnection;
import server.model.board.council.Council;
import server.model.board.council.CouncilorPool;
import server.model.configuration.ConfigurationErrorException;
import server.model.reward.RewardCity;
import javax.xml.parsers.*;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that loads the information from the XML file and generates the whole
 * Map from it.
 * <p>
 * The MapLoader gets from the XML the instructions, the names and all the data
 * necessary to generate the {@link #getRegions() Regions} and
 * {@link #getRegionsNumber() how many they are}, all the
 * {@link #getCitiesList() Cities} distinguishing between all the
 * {@link #getCity(String) single Cities} and the King's one (
 * {@link #getKingCity() the capital}) and the {@link #loadConnections()
 * connections} between them after they {@link #generateConnections() have been
 * generated}.
 *
 * @see City
 * @see CityConnection
 * @see Council
 * @see King
 * @see Region
 */
public class MapLoader {
	private final String xmlPath;
	private List<Region> regions;
	private CouncilorPool pool;
	private List<CityConnection> connections;
	private List<City> citiesOfMap;
	private static final int MAXCONN = 2;

	/**
	 * Initializes the MapLoader saving the XML file and the
	 * {@link CouncilorPool}, loading the whole Map.
	 * 
	 * @param xmlPath
	 *            the path of the XML file that will be saved and loaded
	 * @param pool
	 *            the CouncilorPool used to create the all the Councils
	 * @throws MapXMLFileException
	 * @see MapLoader
	 */
	public MapLoader(String xmlPath, CouncilorPool pool) throws MapXMLFileException {
		this.xmlPath = xmlPath;
		this.pool = pool;
		this.regions = new ArrayList<>();
		this.citiesOfMap = new ArrayList<>();
		loadXML();

	}

	/**
	 * Adds for each {@link City} its connections with the other Cities.
	 * 
	 * @see MapLoader
	 */
	public void loadConnections() {
		for (City c : citiesOfMap) {
			addConnections(c);
		}
	}

	/**
	 * Adds the connection to the {@link City Cities} scanning the list of the
	 * connections and taking those that have the same name of the one received
	 * as parameters.
	 * 
	 * @param c
	 *            the City at which the connection are going to be added
	 * @see MapLoader
	 */
	private void addConnections(City c) {
		for (CityConnection cityConn : connections) {
			if (cityConn.getFirstCity().equals(c.getName())) {
				c.addConnection(searchCity(cityConn.getSecondCity()));
			}
		}
	}

	/**
	 * Returns the {@link City} named as the string received as parameter.
	 * 
	 * @param cityName
	 *            the string that is the name of the searched City
	 * @return the searched City
	 * @see MapLoader
	 */
	private City searchCity(String cityName) {
		for (City c : citiesOfMap)
			if (c.getName().equals(cityName))
				return c;
		return null;
	}

	/**
	 * Scans the XML file and extracts the {@link Region Regions}, then calls
	 * the function which extracts the {@link City Cities} when a Region is
	 * completed, creating them as objects.
	 * 
	 * @throws MapXMLFileException
	 * @see MapLoader
	 */
	private void loadXML() throws MapXMLFileException {
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
				citiesOfMap.addAll(cities);
				if (!pool.canGenerateCouncil()) {
					throw new MapXMLFileException(new ConfigurationErrorException("You need more councilors"));
				}
				regions.add(new Region("name", cities, pool.getCouncil(), 2));
				addRegionToCities(regions.get(i));
			}
		} catch (ParserConfigurationException pec) {
			throw new MapXMLFileException(pec);
		} catch (IOException ioe) {
			throw new MapXMLFileException(ioe);
		} catch (SAXException saxe) {
			throw new MapXMLFileException(saxe);
		}

	}

	/**
	 * Receives a {@link Region} and to each {@link City} it adds a reference to
	 * the Region itself.
	 * 
	 * @param r
	 *            the Region whose Cities are going to receive itself as
	 *            reference
	 * @see MapLoader
	 */
	private void addRegionToCities(Region r) {
		List<City> cities = r.getCities();
		cities.forEach(c -> c.setRegion(r));
	}

	/**
	 * Receives the list of the {@link City Cities} of a {@link Region}. It
	 * extracts the single Regions and calls the function which generates the
	 * City objects.
	 * 
	 * @param citiesOfRegion
	 *            a NodeList of the Cities
	 * @return the City objects
	 * @see MapLoader
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
	 * Receives a {@link City}, it extracts the its parameters and then it
	 * creates the City object.
	 * 
	 * @param cityXML
	 *            the Node of the city
	 * @return a City object
	 * @see MapLoader
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
			generatedCity = new City(Color.decode(color), name);
		else
			generatedCity = new City(Color.decode(color), name, new RewardCity());
		return generatedCity;
	}

	/**
	 * Returns the list of the {@link Region Regions}.
	 * 
	 * @return the list of all the Regions
	 * @see MapLoader
	 */
	public List<Region> getRegions() {
		return regions;
	}

	/**
	 * Returns the capital {@link City}.
	 * 
	 * @return the King's capital
	 * @see MapLoader
	 */
	public City getKingCity() {
		for (City c : citiesOfMap)
			if (c.isCapital())
				return c;
		return null;
	}

	/**
	 * Returns the number of {@link Region Regions}.
	 * 
	 * @return the number of Regions
	 * @see MapLoader
	 */
	public int getRegionsNumber() {
		return regions.size();
	}

	/**
	 * Returns a specific {@link City}.
	 * 
	 * @param name
	 *            the searched City name (case insensitive)
	 * @return the first City with that name; <code>null</code> if nothing found
	 * @see MapLoader
	 */
	public City getCity(String name) {
		for (City c : citiesOfMap) {
			if (c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Returns all the {@link City Cities} of this Map.
	 * 
	 * @return all the Cities
	 * @see MapLoader
	 */
	public List<City> getCitiesList() {
		return this.citiesOfMap;
	}

	/**
	 * Generates the connections of each {@link City} randomly.
	 * 
	 * @see MapLoader
	 * @see Random
	 */
	public void generateConnections() {
		for (Region r : regions) {
			for (City c : r.getCities()) {
				generateConnectionsForACity(c, r);
			}
		}
		addRegionConnections();
	}

	/**
	 * Generates the connections of a single {@link City} randomly.
	 * 
	 * @param c
	 *            the City that is considered
	 * @param r
	 *            a Region of the Map
	 * @see MapLoader
	 */
	private void generateConnectionsForACity(City c, Region r) {
		Random random = new Random();
		int connectionsNumber = 1 + random.nextInt(MAXCONN);
		while (c.getConnectedCities().size() < connectionsNumber) {
			for (int i = -3; i <= 3; i++) {
				if (i == 0)
					continue;
				int cityIndex = r.getCities().indexOf(c);
				if ((cityIndex + i) >= 0 && (cityIndex + i) < r.getCities().size()
						&& !c.isConnectedTo(r.getCities().get(cityIndex + i)) && random.nextBoolean()) {
					c.addConnection(r.getCities().get(cityIndex + i));
					r.getCities().get(cityIndex + i).addConnection(c);
				}
			}
		}
	}

	/**
	 * Adds the generated connections to each {@link Region Regions}.
	 * 
	 * @see MapLoader
	 */
	private void addRegionConnections() {
		Random r = new Random();
		for (int i = 0; i < regions.size() - 1; i++) {
			for (int j = 0; j < regions.get(i).getCities().size(); j++) {
				if ((j % 2 == 1 || j == regions.get(i).getCities().size() - 1) && r.nextInt(5) < 4) {
					int secondIndex = i * 5 + j + ((j == regions.get(i).getCities().size() - 1) ? 5 : 4);
					regions.get(i).getCities().get(j).addConnection(citiesOfMap.get(secondIndex));
					citiesOfMap.get(secondIndex).addConnection(regions.get(i).getCities().get(j));
				}
			}
		}

	}

}
