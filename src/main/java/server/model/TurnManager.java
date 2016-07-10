package server.model;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.control.Controller;
import server.control.connection.ClientInt;
import server.control.instruction.notify.NotifyPlayerDisconnected;
import server.control.instruction.update.NotifyTurnEnded;
import server.control.instruction.update.UpdatePlayer;
import server.control.instruction.update.NotifyYourTurn;
import server.model.action.Action;
import server.model.player.Player;
import server.model.player.PoliticCard;

/**
 * A class that manages the turn of each Player in the Game.
 * <p>
 * Each turn {@link #playTurn(int) is started} by the Game with the current
 * Player and it's normally ended when he has no more Actions left. During a
 * turn, the Controller communicates to the TurnManager the Actions the Player
 * {@link #performAction(Action) wants to do} or if he is intentioned to
 * {@link #setWantToEnd() end his turn} earlier after he has completed all his
 * available MainActions.
 * 
 * @author Matteo Colombo
 * @see Action
 * @see Controller
 * @see Game
 * @see Player
 */
public class TurnManager {
	private int playerIndex;
	private boolean playerWantsToExit;
	private static Logger logger = Logger.getGlobal();
	private List<Player> players;
	private List<Color> colors;

	/**
	 * Initializes the TurnManager with all the {@link Player Players} and the
	 * available {@link Color Colors} used for the {@link PoliticCard
	 * PoliticCards}.
	 * 
	 * @param players
	 *            the list of all the Players
	 * @param colors
	 *            the list of the Colors used for the PoliticCards
	 * @see TurnManager
	 */
	public TurnManager(List<Player> players, List<Color> colors) {
		this.players = players;
		this.colors = colors;
	}

	/**
	 * Manages the turn of the current {@link Player} until he has no
	 * {@link Action Actions} left or he wants to end his turn earlier after he
	 * has completed all his available MainActions.
	 * <p>
	 * This is called by the Game.
	 * 
	 * @param playerIndex
	 *            the index of the current Player
	 * @see TurnManager
	 */
	public void playTurn(int playerIndex) {
		playerWantsToExit = false;
		this.playerIndex = playerIndex;
		this.players.get(playerIndex).actionsReset();
		Player turnPlayer = players.get(playerIndex);

		notifyTurnStarted(turnPlayer);

		while (!(turnPlayer.getIfExtraActionDone() && turnPlayer.getMainActionsLeft() == 0)
				&& !(turnPlayer.getMainActionsLeft() == 0 && playerWantsToExit)) {
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
	 * Sends a notification to the current {@link Player} notifying him that
	 * it's his turn to play.
	 * 
	 * @param turnPlayer
	 *            the current Player
	 * @see TurnManager
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
	 * Sends a notification to the current {@link Player} notifying him that his
	 * turn is ended.
	 * 
	 * @param turnPlayer
	 *            the current Player
	 * @see TurnManager
	 */
	private void notifyTurnEnded(Player turnPlayer) {
		try {
			turnPlayer.getClient().notify(new NotifyTurnEnded());
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}

	/**
	 * Executes an {@link Action} of the current {@link Player}.
	 * <p>
	 * This is called by the {@link Controller}.
	 * 
	 * @param a
	 *            the chosen Action
	 * @see TurnManager
	 */
	public void performAction(Action a) {
		a.execute();
	}

	/**
	 * Ends the turn of the current {@link Player} even if he could have still
	 * performed his {@link Action ExtraAction}.
	 * <p>
	 * This is called by the {@link Controller}.
	 * 
	 * @see TurnManager
	 */
	public void setWantToEnd() {
		this.playerWantsToExit = true;
	}

	/**
	 * Sends to each {@link ClientInt Client} the update of the new current
	 * {@link Player}.
	 * 
	 * @throws IOException
	 * @see TurnManager
	 */
	private void notifyUpdatePlayer() throws IOException {
		for (Player p : players) {
			if (!p.getSuspended())
				p.getClient().notify(new UpdatePlayer(players.get(playerIndex).getClientCopy(), playerIndex));
		}
	}

	/**
	 * Notifies the disconnection of a {@link Player} to all the others.
	 * 
	 * @param name
	 *            the name of the disconnected Player
	 * @throws IOException
	 * @see TurnManager
	 */
	private void notifyDisconneted(String name) throws IOException {
		for (Player p : players) {
			if (!p.getSuspended())
				p.getClient().notify(new NotifyPlayerDisconnected(name));
		}
	}
}
