package game;

import game.board.Board;
import game.player.Player;

public class TurnManager {
	private Player turnPlayer;
	private Board gameBoard;
	public TurnManager(Player turnPlayer, Board gameBoard){
		this.turnPlayer=turnPlayer;
		this.turnPlayer.actionsReset();
		this.gameBoard=gameBoard;
	}
	
}
