package client.view;

import java.util.List;

import client.model.GameProperty;
import model.board.Region;

public interface ViewInterface{
	
	public void printAskPlayersNumber(int max);
	
	public void printAskWhichMapToUse(List<String> maps);
	
	public void printAskWhatActionToDo();
	
	public void printCities(List<Region> regions);
	
	public void printAskPlayerName();
	
	public void printIllegalAction();
	
	public void showInitMenu();
	
	public void showGetConnectionType();
	
	/**
	 * this give to the caller (a Dialogue probably) the access to the local model,
	 * and update the changes done on the model in the server.
	 * The local model is implemented with java Fx Collection Classes
	 * @return the local Model
	 */
	public GameProperty getLocalModel();
	
}	
