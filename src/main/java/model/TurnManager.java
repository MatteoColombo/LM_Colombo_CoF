package model;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.action.Action;
import model.player.Player;
import model.player.PoliticCard;
import view.p2pdialogue.update.NotifyTurnEnded;
import view.p2pdialogue.update.NotifyUpdatePlayer;
import view.p2pdialogue.update.NotifyYourTurn;

/**
 * This class is the one which manages a player's turn
 * 
 * @author Matteo Colombo
 *
 */
public class TurnManager {
	private int playerIndex;
	private boolean playerWantsToExit;
	private static Logger logger = Logger.getGlobal();
	private List<Player> players;
	private List<Color> colors;

	public TurnManager(List<Player> players, List<Color> colors) {
		this.players = players;
		this.colors = colors;
	}

	/**
	 * This method is the one which is called by the Game class, it manages the
	 * player turn and goes on until when the player has no actions left or when
	 * he wants to end and has completed all its main actions
	 */
	public void startTurn(int playerIndex) {
		playerWantsToExit = false;
		this.playerIndex = playerIndex;
		this.players.get(playerIndex).actionsReset();
		Player turnPlayer = players.get(playerIndex);
		try {
			turnPlayer.getPoliticCard().add(new PoliticCard(colors));
			notifyUpdatePlayer();
			turnPlayer.getClient().notify(new NotifyYourTurn());
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			turnPlayer.setSuspension(true);
			return;
		}

		while (!(turnPlayer.getIfExtraActionDone() && turnPlayer.getMainActionsLeft() == 0) && !(turnPlayer.getMainActionsLeft() == 0 && playerWantsToExit)) {
			try {
				if (turnPlayer.getClient().isConnected())
					turnPlayer.getClient().askPlayerWhatActionToDo();
				else {
					turnPlayer.setSuspension(true);
					return;
				}
			} catch (IOException e) {
				turnPlayer.setSuspension(true);
				turnPlayer.getClient().close();
				logger.log(Level.WARNING, e.getMessage(), e);
				return;
			}
		}
		try {
			turnPlayer.getClient().notify(new NotifyTurnEnded());
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			turnPlayer.setSuspension(true);
			return;
		}
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

	public void notifyUpdatePlayer() throws IOException {
		for (Player p : players) {
			if (!p.getSuspended())
				p.getClient().notify(new NotifyUpdatePlayer(players.get(playerIndex).getClientCopy(), playerIndex));
		}
	}

}
