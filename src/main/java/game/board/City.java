package game.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import game.player.*;
import game.reward.*;

public class City {
	private String cityName;
	private Color cityColor;
	private Reward cityReward;
	private List<Emporium> emporiums;
	private final boolean capital;
	
	public City(Color cityColor, String cityName, Reward cityReward) {
		this.cityName = cityName;
		this.cityColor = cityColor;
		this.emporiums = new ArrayList<>();
		this.cityReward = cityReward;
		this.capital=false;
	}
	
	public City(Color cityColor, String cityName, Reward cityReward, boolean capital) {
		this.cityName = cityName;
		this.cityColor = cityColor;
		this.emporiums = new ArrayList<>();
		this.cityReward = cityReward;
		this.capital=capital;
	}
	
	public boolean isCapital(){
		return capital;
	}

	public String getName() {
		return cityName;
	}

	public Color getColor() {
		return cityColor;
	}

	public void addEmporium(Emporium e){
		this.emporiums.add(e);
	}

	public boolean hasEmporiumOfPlayer(Player p) {
		for (Emporium e : emporiums) {
			if (p.equals(e.getPlayer()))
				return true;
		}
		return false;
	}

	public Reward getReward() {
		return cityReward;
	}
	
	public int getNumberOfEmporium() {
		return this.emporiums.size();
	}

}
