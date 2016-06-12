package view.server;

import java.io.IOException;
import java.util.List;

import control.Controller;
import model.player.Player;

public interface ClientInt {
	// TODO everything
	public void setController(Controller controller);

	public void askPlayerWhatActionToDo() throws IOException;

	public String getName()  throws IOException;

	public void askConfiguration(List<String> maps, int maxNumberOfPlayers) throws IOException;
		
	public void close();
	
	public void askPlayerName() throws IOException;
	
	public void notifyIllegalAction();
	
	public boolean isConnected();
	
	public void notifyGameLoading() throws IOException;
	
	public void notifyGameStarted() throws IOException;
	
	public void notifyYourTurn() throws IOException;
	
	public void notifyAnotherPlayerTurn() throws IOException;

	public void askPlayerWhichMerchandiseBuy(Player buyingPlayer, List<Player> allPlayers) throws IOException;

	public boolean askPlayerConfirmation() throws IOException;
}
