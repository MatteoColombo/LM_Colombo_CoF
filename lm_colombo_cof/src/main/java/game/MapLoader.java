package game;

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

	public MapLoader(String xmlPath, CouncilorPool pool) {
		this.xmlPath = "map.xml";
		this.pool = pool;
		loadXML();
	}

	public void loadXML() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// connections= new ArrayList<>();
		try {
			File xml = new File(xmlPath);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlDoc = builder.parse(xml);
			xmlDoc.getDocumentElement().normalize();
			// root
			Element root = xmlDoc.getDocumentElement();
			// regions
			NodeList regionsList = root.getElementsByTagName("region");
			System.out.println(regionsList.getLength());
			for(int i=0; i< regionsList.getLength(); i++){
				Node region= regionsList.item(0);
				NodeList citiesOfRegion= region.getChildNodes();
				System.out.println("Region "+i+" has "+citiesOfRegion.getLength()+" cities");
				for(int j=0; j< citiesOfRegion.getLength();j++){
					
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public List<Region> getRegions() {
		return regions;
	}

	public static void main(String[] args) {
		List colors = new ArrayList<>();
		colors.add(Color.BLACK);
		colors.add(Color.BLUE);
		colors.add(Color.CYAN);
		MapLoader ml = new MapLoader("map.xml", new CouncilorPool(4, 3, colors));

	}

	public City getKingCity() {
		return null;
	}

	public int getRegionsNumber() {
		return 0;
	}
}
