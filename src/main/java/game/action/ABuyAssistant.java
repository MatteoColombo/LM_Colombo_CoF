package game.action;

import game.board.exceptions.NegativeException;
import game.player.Player;

public class ABuyAssistant extends Action {
	private static final int ASSISTANTPRICE = 3;
	
	Player player;
	
	public ABuyAssistant(Player player) {
		super(false);
		this.player = player;
	}
	@Override
	public void execute() throws IllegalActionException {
		try {
			player.getCoins().decrease(ASSISTANTPRICE);
			player.getAssistants().increment(1);
		} catch(NegativeException e) {
			throw new IllegalActionException();
		}
	}
	
}
