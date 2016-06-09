package view.client;

import java.util.List;

import model.board.Region;

public interface ViewInterface {
	
	public void printAskPlayersNumber(int max);
	
	public void printAskWhichMapToUse(List<String> maps);
	
	public void printAskWhatActionToDo();
	
	public void printCities(List<Region> regions);
	
	public void printAskPlayerName();
	
	public void printIllegalAction();
}	
