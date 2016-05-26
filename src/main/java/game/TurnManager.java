package game;

import connections.rmi.Local;
import game.action.ABuyAssistant;
import game.action.AExtraMainAction;
import game.action.Action;
import game.board.Board;
import game.exceptions.IllegalActionException;
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
	// this method should implement RMI, and be invocated by the client
	private Action computeRequest(String request) throws IllegalActionException {
		String[] tokenized = request.split(" ");
			// TODO understand the request
			/*
			 * possible requests:
			 * 
			 * 1) build emporium in <city name> with <permission card>
			 * 2) build emporium with king in <city name> with <cards..>
			 * 3) buy assistant
			 * 4) buy permission card <permission card> with <cards..>
			 * 5) buy extra main action
			 * 6) shuffle cards in <region>
			 * 7) slide council in <region/king> with <color> 
			 * 8) slide council as side action in <region/king> with <color> 
			 * 
			 */
			switch(tokenized[0]) {
			case "buy":
				switch(tokenized[1]) {
				case "assistant":
					return new ABuyAssistant(turnPlayer);
				case "action":
					return new AExtraMainAction(turnPlayer);
					// and so on, but it is hard coded
				}
			}
		return null;
	}
}
