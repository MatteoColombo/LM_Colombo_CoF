package client.cli.model;

/**
 * This is the CliBonus class, it's used to save a bonus in the cli model
 * @author Matteo Colombo
 *
 */
public class CliBonus {
	private String name;
	private int value;
	
	/**
	 * This is the constructor of the cli bonus
	 * @param value the value of the bonus (e.g. 2 coins, ecc)
	 * @param name the name of the bonus, it's the one which is printed in the cli
	 */
	public CliBonus(int value, String name){
		this.name=name;
		this.value=value;
	}
	
	/**
	 * 
	 * @return the value of the bonus
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * 
	 * @return the name of the bonus
	 */
	public String getName(){
		return this.name;
	}
}
