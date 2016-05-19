package game.action;

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
		if(player.getCoins().getAmount() < ASSISTANTPRICE) {
			throw new IllegalActionException();
		}
		
		player.getCoins().decrease(ASSISTANTPRICE);
		player.getAssistants().increment(1);
	}
	
}
