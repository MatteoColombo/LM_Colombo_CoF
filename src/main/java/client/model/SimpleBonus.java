package client.model;

public class SimpleBonus {
	String name;
	int amount;
	
	public SimpleBonus(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAmount() {
		return this.amount;
	}
}
