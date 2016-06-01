package model;

import model.action.Action;
import model.board.Board;
import model.player.Player;

public class TurnManager {
	private Player turnPlayer;
	
	public TurnManager(Player turnPlayer, Board gameBoard){
		//TODO
		this.turnPlayer=turnPlayer;
		this.turnPlayer.actionsReset();
		startTurn();
	}
	private void startTurn(){
		askPlayerWhatToDo();
	}
	
	public void performAction(Action a) {
		a.execute();
	}
	
	private void askPlayerWhatToDo(){
		//TODO
	}
}
