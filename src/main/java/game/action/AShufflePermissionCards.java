package game.action;

import game.board.Region;
import game.exceptions.IllegalActionException;
import game.player.Player;

public class AShufflePermissionCards extends Action{
	private static final int ACTIONCOST = 1;
	private Player player;
	private Region region;
	
	public AShufflePermissionCards(Player player, Region region) throws IllegalActionException{
		super(false);
		if(player.getAssistants().getAmount() < ACTIONCOST) {
			throw new IllegalActionException("you can not afford it!");
		}
		this.player = player;
		this.region = region;
	}

	@Override
	public void execute() {
		player.getAssistants().decreaseAmount(ACTIONCOST);
		region.shufflePermissionCards();
	}
}
