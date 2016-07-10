package server.model;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.control.instruction.notify.NotifyPlayerDisconnected;
import server.control.instruction.update.NotifyTurnEnded;
import server.control.instruction.update.UpdatePlayer;
import server.control.instruction.update.NotifyYourTurn;
import server.model.action.Action;
import server.model.player.Player;
import server.model.player.PoliticCard;

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

	/**
	 * 
	 * @param players
	 *            the list of the players
	 * @param colors
	 *            the list of colors used by the politic cards
	 */
	public TurnManager(List<Player> players, List<Color> colors) {
		this.players = players;
		this.colors = colors;
	}

	/**
	 * This method is the one which is called by the Game class, it manages the
	 * player turn and goes on until when the player has no actions left or when
	 * he wants to end and has completed all its main actions
	 */
	public void playTurn(int playerIndex) {
		playerWantsToExit = false;
		this.playerIndex = playerIndex;
		this.players.get(playerIndex).actionsReset();
		Player turnPlayer = players.get(playerIndex);

		notifyTurnStarted(turnPlayer);

		while (!(turnPlayer.getIfExtraActionDone() && turnPlayer.getMainActionsLeft() == 0) && !(turnPlayer.getMainActionsLeft() == 0 && playerWantsToExit)) {
			try {
				if (turnPlayer.getClient().isConnected())
					turnPlayer.getClient().askPlayerWhatActionToDo();
				else {
					turnPlayer.setSuspension(true);
					turnPlayer.getClient().close();
					notifyDisconneted(turnPlayer.getName());
					return;
				}
			} catch (IOException e) {
				turnPlayer.setSuspension(true);
				turnPlayer.getClient().close();
				try {
					notifyDisconneted(turnPlayer.getName());
				} catch (Exception e2) {
					logger.log(Level.WARNING, e2.getMessage(), e2);
				}
				logger.log(Level.WARNING, e.getMessage(), e);
				return;
			}
		}
		turnPlayer.doExtraAction();
		notifyTurnEnded(turnPlayer);
	}

	/**
	 * Sends a notification to the current player notifying him that it's its
	 * turn to play
	 * 
	 * @param turnPlayer
	 */
	private void notifyTurnStarted(Player turnPlayer) {
		try {
			turnPlayer.getPoliticCard().add(new PoliticCard(colors));
			notifyUpdatePlayer();
			turnPlayer.getClient().notify(new NotifyYourTurn());
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}

	/**
	 * Sends a notification to the player to notify him about the end of its
	 * turn
	 * 
	 * @param turnPlayer
	 */
	private void notifyTurnEnded(Player turnPlayer) {
		try {
			turnPlayer.getClient().notify(new NotifyTurnEnded());
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
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

	/**
	 * Sends to each client the update of the current player
	 * 
	 * @throws IOException
	 */
	private void notifyUpdatePlayer() throws IOException {
		for (Player p : players) {
			if (!p.getSuspended())
				p.getClient().notify(new UpdatePlayer(players.get(playerIndex).getClientCopy(), playerIndex));
		}
	}

	private void notifyDisconneted(String name) throws IOException {
		for (Player p : players) {
			if (!p.getSuspended())
				p.getClient().notify(new NotifyPlayerDisconnected(name));
		}
	}
}
