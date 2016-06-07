package model;

import java.io.IOException;

import model.action.Action;
import model.player.Player;

public class TurnManager {
	private Player turnPlayer;
	private boolean playerWantsToExit;

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
				e.printStackTrace();
			}
		}
	}

	public void performAction(Action a) {
		a.execute();
	}

}
