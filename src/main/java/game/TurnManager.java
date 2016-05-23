package game;

import game.board.Board;
import game.player.Player;

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
	
	private void askPlayerWhatToDo(){
		//TODO
	}
	
	
	
}
