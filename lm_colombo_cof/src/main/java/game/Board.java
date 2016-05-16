package game;

import java.awt.Color;
import java.util.List;


public class Board {
	private final int NUM_REG;
	private final String xmlPath;
	private final int councPerColor;
	private final int concSize;
	private final List<Color> colors;
	private List<Region> regions;
	private MapLoader xmlManager;
	private NobleTrack track;
	private King mapKing;
	private CouncilorPool councilManager;

	public Board(int numReg, String xmlPath, int councPerColor, int concSize, List<Color> colors) throws ConfigurationErrorException{
		this.NUM_REG = numReg;
		this.xmlPath = xmlPath;
		this.concSize = concSize;
		this.colors=colors;
		this.councPerColor = councPerColor;
		initializeBoard();
	}

	private void initializeBoard() throws ConfigurationErrorException{
		this.xmlManager = new MapLoader(xmlPath,councilManager);
		this.track = new NobleTrack();
		this.councilManager = new CouncilorPool(councPerColor,concSize,colors);
		try{
			this.mapKing = new King(xmlManager.getKingCity(), councilManager.getCouncil());
		}catch(CouncilorNotAvailableException cnae){
			throw new ConfigurationErrorException();
		}
		this.regions=xmlManager.getRegions();
	}

	// Regions part
	public int getRegionsNumber() {
		return this.regions.size();
	}

	public Region getRegion(int posArray) {
		return regions.get(posArray);
	}

	public Council getRegionCouncil(int posArray) {
		return regions.get(posArray).getCouncil();
	}

	// Noble track part

	public NobleTrack getNobleTrack() {
		return this.track;
	}

	// King part

	public void moveKing(City newKingCity) {
		this.mapKing.moveKing(newKingCity);
	}

	public City getKingLocation() {
		return this.mapKing.getKingLocation();
	}

	public Council getKingCouncil() {
		return this.mapKing.getKingCouncil();
	}

	// City Part

}
