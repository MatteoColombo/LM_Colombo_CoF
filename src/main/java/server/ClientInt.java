package server;

import java.io.IOException;

import control.Controller;

public interface ClientInt {
	// TODO everything
	public void setController(Controller controller);
	
	public void askPlayerWhatActionToDo() throws IOException;
}
