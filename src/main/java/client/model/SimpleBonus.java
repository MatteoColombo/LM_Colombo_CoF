package client.model;

public class SimpleBonus {
	private String name;
	private int amount;
	
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
