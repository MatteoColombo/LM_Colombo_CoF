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
		startTurn();
	}

	private void startTurn() {
		while (!playerWantsToExit || (!turnPlayer.getIfExtraActionDone() && turnPlayer.getMainActionsLeft() > 0)) {
			try {
				this.turnPlayer.getClient().askPlayerWhatActionToDo();
			} catch (IOException e) {
				turnPlayer.setSuspension(true);
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

	public void performAction(Action a) {
		a.execute();
	}

}
