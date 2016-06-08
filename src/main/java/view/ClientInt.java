package view;

import java.io.IOException;
import java.util.List;

import control.Controller;

public interface ClientInt {
	// TODO everything
	public void setController(Controller controller);

	public void askPlayerWhatActionToDo() throws IOException;

	public String getName()  throws IOException;

	public void askMaxNumberOfPlayers(Integer maxNumberOfPlayers)  throws IOException;

	public void askWichMapToUse(List<String> maps)  throws IOException;
	
	public void close();
	
	public void askPlayerName() throws IOException;
}
