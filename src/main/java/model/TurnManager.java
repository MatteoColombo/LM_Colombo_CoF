package model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.action.Action;
import model.player.Player;

public class TurnManager {
	private Player turnPlayer;
	private boolean playerWantsToExit;
	private static Logger logger = Logger.getGlobal();
	
	public TurnManager(Player turnPlayer) {
		playerWantsToExit = false;
		this.turnPlayer = turnPlayer;
		this.turnPlayer.actionsReset();
	}

	public void startTurn() {
		while (!playerWantsToExit || (!turnPlayer.getIfExtraActionDone() && turnPlayer.getMainActionsLeft() > 0)) {
			try {
				if(this.turnPlayer.getClient().isConnected())				
					this.turnPlayer.getClient().askPlayerWhatActionToDo();
				else{
					System.out.println("questo è down");
					turnPlayer.setSuspension(true);
				}
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
				break;
			}
		}
	}

	public void performAction(Action a) {
		a.execute();
	}

}
