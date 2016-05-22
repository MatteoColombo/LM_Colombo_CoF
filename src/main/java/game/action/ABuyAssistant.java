package game.action;

import game.exceptions.IllegalActionException;
import game.player.Player;

public class ABuyAssistant extends Action {
	private static final int ASSISTANTPRICE = 3;
	
	Player player;
	
	public ABuyAssistant(Player player) throws IllegalActionException{
		super(false);
		if(player.getCoins().getAmount() < ASSISTANTPRICE) {
			throw new IllegalActionException("you can not afford it!");
		}
		this.player = player;
	}
	@Override
	public void execute() {
		player.getCoins().decreaseAmount(ASSISTANTPRICE);
		player.getAssistants().increaseAmount(1);
	}
	
}
