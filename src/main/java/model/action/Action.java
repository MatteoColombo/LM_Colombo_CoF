package model.action;

import model.exceptions.IllegalActionException;
import model.player.Player;

public abstract class Action {
	private boolean main;
	
	public Action() {
		//empty
	}
	
	public Action(boolean main, Player player) throws IllegalActionException {
		this.main = main;
		if(!checkLegal(player)) {
			throw new IllegalActionException("you can not do more action of this type");
		}
	}
	
	public boolean isMain() {
		return main;
	}
	
	public abstract void execute();
	
	public boolean checkLegal(Player p) {
		if(main && p.getMainActionsLeft() > 0) {
			return true;
		}
		
		if(!main && !p.getIfExtraActionDone()) {
			return true;
		}
		
		return false;
	}
}
