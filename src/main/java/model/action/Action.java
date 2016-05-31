package model.action;

public abstract class Action {
	private boolean main;
	
	public Action(boolean main) {
		this.main = main;
	}
	
	public boolean isMain() {
		return main;
	}
	
	public abstract void execute();
}
