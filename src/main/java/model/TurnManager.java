package model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.action.Action;
import model.player.Player;

/**
 * This class is the one which manages a player's turn
 * @author Matteo Colombo
 *
 */
public class TurnManager {
	private Player turnPlayer;
	private boolean playerWantsToExit;
	private static Logger logger = Logger.getGlobal();

	public TurnManager(Player turnPlayer) {
		playerWantsToExit = false;
		this.turnPlayer = turnPlayer;
		this.turnPlayer.actionsReset();
	}

	/**
	 * This method is the one which is called by the Game class, it manages the
	 * player turn and goes on until when the player has no actions left or when
	 * he wants to end and has completed all its main actions
	 */
	public void startTurn() {
		try {
			System.out.println("ho notificato");
			this.turnPlayer.getClient().notifyYourTurn();
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(),e );
			this.turnPlayer.setSuspension(true);
			return;
		}
		
		while (!playerWantsToExit || (!turnPlayer.getIfExtraActionDone() && turnPlayer.getMainActionsLeft() > 0)) {
			try {
				if (this.turnPlayer.getClient().isConnected())
					this.turnPlayer.getClient().askPlayerWhatActionToDo();
				else {
					turnPlayer.setSuspension(true);
					return;
				}
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
				return;
			}
		}
		System.out.println("player è uscito");
	}

	/**
	 * This method is called by the controller and it executes an action
	 * 
	 * @param a
	 */
	public void performAction(Action a) {
		a.execute();
	}

	/**
	 * This method is called by the controller want the player can and wants to
	 * end its turn without doing his extra action
	 */
	public void setWantToEnd() {
		this.playerWantsToExit = true;
	}

}
