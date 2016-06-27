package client.viewCLI.model;

import java.util.ArrayList;
import java.util.List;

public class CliCity {
	private String name;
	private List<String> connections;
	private boolean hasKing;
	private List<CliBonus> rewards;
	private List<String> emporiums;

	/**
	 * Initializes a city with the parameters that can be loaded by file
	 * @param name the name of the city
	 * @param connections the list of the connections
	 * @param hasKing true if the city is capital, false otherwise
	 */
	public CliCity(String name, List<String> connections, boolean hasKing) {
		this.name = name;
		this.connections = connections;
		this.hasKing = hasKing;
		this.emporiums= new ArrayList<>();
	}

	/**
	 * Returns the name of the city
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the list with the names of the connected cities
	 * @return
	 */
	public List<String> getConnections() {
		return connections;
	}

	/**
	 * Return if the city is hosting the king
	 * @return true if there's the king, false otherwise
	 */
	public boolean isHasKing() {
		return hasKing;
	}
	
	/**
	 * This is used when the king moves from a city to another
	 * @param hasKing
	 */
	public void setHasKing(boolean hasKing){
		this.hasKing=hasKing;
	}

	/**
	 * Sets the bonus which the city holds
	 * @param rewards the reward
	 */
	public void setBonus(List<CliBonus> rewards){
		this.rewards=rewards;
	}
	
	/**
	 * returns the bonus holden by the city
	 * @return
	 */
	public List<CliBonus> getBonus(){
		return this.rewards;
	}
	
	/**
	 * It is used when a player builds an emporium in this city
	 * @param owner
	 */
	public void addEmporium(String owner){
		emporiums.add(owner);
	}
	
	/**
	 * Returns the list of the emporium which in fact is a list of names
	 * 
	 * @return
	 */
	public List<String> getEmporiums(){
		return emporiums;
	}
	
}
