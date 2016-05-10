package game;

import java.awt.Color;
import java.util.ArrayList;

public class City {
	private String cityName;
	private Color cityColor;
	// private Reward cityReward;
	private ArrayList<Emporium> emporiums;

	public City(Color cityColor, String cityName /* Reward cityReward */) {
		this.cityName = cityName;
		this.cityColor = cityColor;
		this.emporiums= new ArrayList<Emporium>();
		//this.cityReward=cityReward;
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
	//I need to pass the player
	public boolean hasEmporiumOfPlayer(){
		for(Emporium e: emporiums){
			//I need to check if it exists an emporium of the received player
			if(true)
				return true;
		}
		return false;
	}
	
	/*
	private Reward getReward(){
		return cityReward;
	}*/

}
