package client.model.cli;

import java.util.List;

public class CliCity {
	private String name;
	private List<String> connections;
	private boolean hasKing;
	private List<CliBonus> rewards;

	public CliCity(String name, List<String> connections, boolean hasKing) {
		this.name = name;
		this.connections = connections;
		this.hasKing = hasKing;
	}

	public String getName() {
		return name;
	}

	public List<String> getConnections() {
		return connections;
	}

	public boolean isHasKing() {
		return hasKing;
	}

	public void setBonus(List<CliBonus> rewards){
		this.rewards=rewards;
	}
	
	public List<CliBonus> getBonus(){
		return this.rewards;
	}
	
}
