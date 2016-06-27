package client.viewCLI.model;

public class CliBonus {
	private String name;
	private int value;
	
	public CliBonus(int value, String name){
		this.name=name;
		this.value=value;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getName(){
		return this.name;
	}
}
