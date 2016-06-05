package model;

import java.io.IOException;

import model.action.Action;
import model.board.Board;
import model.player.Player;

public class TurnManager {
	private Player turnPlayer;
	private Board gameBoard;
	public TurnManager(Player turnPlayer, Board gameBoard){
		this.gameBoard=gameBoard;
		this.turnPlayer=turnPlayer;
		this.turnPlayer.actionsReset();
		startTurn();
	}
	private void startTurn(){
		try {
			this.turnPlayer.getClient().askPlayerWhatActionToDo();
		} catch (IOException e) {
			turnPlayer.setSuspension(true);
			e.printStackTrace();
		}
	}
	
	public void performAction(Action a) {
		a.execute();
	}
	
	private void askPlayerWhatToDo(){
		//TODO
	}
}
