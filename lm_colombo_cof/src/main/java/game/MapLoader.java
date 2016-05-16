package game;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.cycle.UndirectedCycleBase;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.w3c.dom.*;

import javax.print.Doc;
import javax.xml.parsers.*;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {
	private final String xmlPath;
	private int regionNumber;
	private List<Region> regions;
	private CouncilorPool pool;
	private List<CityConnection> connections;
	//private UndirectedGraph<String, DefaultEdge> graph;
	private Graph<City, DefaultEdge> graph;
	
	public MapLoader(String xmlPath, CouncilorPool pool) {
		this.xmlPath = "src/main/resources/map.xml";
		this.pool = pool;
		this.regions= new ArrayList<>();
		//this.graph= new ListenableUndirectedGraph<>(DefaultEdge.class);
		loadXML();
		//generateGraph();
	
	}

	public void loadXML() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		connections= new ArrayList<>();
		try {
			File xml = new File(xmlPath);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xml);
			xmlDoc.getDocumentElement().normalize();
			Element root = xmlDoc.getDocumentElement();
			NodeList regionsList = root.getElementsByTagName("region");
			for(int i=0; i< regionsList.getLength(); i++){
				List<City> cities= new ArrayList<>();
				Node region= regionsList.item(0);
				NodeList citiesOfRegion= region.getChildNodes();
				for(int j=0; j< citiesOfRegion.getLength();j++){
					Node city=citiesOfRegion.item(j);
					boolean isCapital= Boolean.valueOf(city.getAttributes().getNamedItem("capital").getNodeValue()).booleanValue();
					NodeList cityattr= city.getChildNodes();
					String name="";
					String color="";
					List<String> connectedCities= new ArrayList<>();
					for(int k=0; k< cityattr.getLength(); k++){
						Node attr= cityattr.item(k);
						String attrType=attr.getNodeName();
						switch(attrType) {
						case "name":
							name=attr.getTextContent();
							break;
						case "color":
							color=attr.getTextContent();
							break;
						case "connection":
							connectedCities.add(attr.getTextContent());
							break;
						default:
							break;
						}
					}
					for(String temp: connectedCities)
						connections.add(new CityConnection(name, temp));
					if(isCapital)
						cities.add(new City(Color.decode(color), name, new Reward()));
					else
						cities.add(new City(Color.decode(color), name, new Reward(),isCapital));
				}
				regions.add(new Region("name", cities, pool.getCouncil(), 2));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void generateGraph(){
		addCitiesToGraph();
		
	}
	
	public void addCitiesToGraph(){
		for(Region r: regions){
			List<City> tempCities= r.getCities();
			for(City c: tempCities)
				graph.addVertex(c);
		}
	}
	
	
	public List<Region> getRegions() {
		return regions;
	}
	
	public static void main(String[] args) {
		List<Color> colors = new ArrayList<>();
		colors.add(Color.BLACK);
		colors.add(Color.BLUE);
		colors.add(Color.ORANGE);
		colors.add(Color.WHITE);
		colors.add(Color.PINK);
		MapLoader ml = new MapLoader("map.xml", new CouncilorPool(4, 4, colors));

	}

	public City getKingCity() {
		return null;
	}

	public int getRegionsNumber() {
		return 0;
	}
}
