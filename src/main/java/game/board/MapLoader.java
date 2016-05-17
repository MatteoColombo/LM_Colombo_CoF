package game.board;


import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {
	private final String xmlPath;
	private List<Region> regions;
	private CouncilorPool pool;
	private List<CityConnection> connections;
	public MapLoader(String xmlPath, CouncilorPool pool) throws Exception {
		this.xmlPath = xmlPath;
		this.pool = pool;
		this.regions = new ArrayList<>();
		loadXML();

	}

	public void loadXML() throws Exception{
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
				List<City> cities = new ArrayList<>();
				Node region = regionsList.item(i);
				NodeList citiesOfRegion = region.getChildNodes();
				for (int j = 0; j < citiesOfRegion.getLength(); j++) {
					Node city = citiesOfRegion.item(j);
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
						cities.add(new City(Color.decode(color), name, new Reward()));
					else
						cities.add(new City(Color.decode(color), name, new Reward(), isCapital));
				}
				regions.add(new Region("name", cities, pool.getCouncil(), 2));
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Region> getRegions() {
		return regions;
	}

	public City getKingCity() {
		return null;
	}

	public int getRegionsNumber() {
		return 0;
	}
}