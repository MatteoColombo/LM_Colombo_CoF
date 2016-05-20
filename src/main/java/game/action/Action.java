package game.action;

import game.exceptions.IllegalActionException;

public abstract class Action {
	private boolean main;
	
	public Action(boolean main) {
		this.main = main;
	}
	
	public boolean isMain() {
		return main;
	}
	
	public abstract void execute() throws IllegalActionException;
}
