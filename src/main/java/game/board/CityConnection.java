package game.board;

import java.util.ArrayList;
import java.util.List;

public class CityConnection {
	private List<String> cities;
	
	public CityConnection(String firstCity, String secondCity){
		this.cities= new ArrayList<>();
		cities.add(firstCity);
		cities.add(secondCity);
	}
	
	public List<String> getConnection(){
		return cities;
	}
	public String getFirstCity(){
		return cities.get(0);
	}
	public String getSecondCity(){
		return cities.get(1);
	}
}
