package model.action;

import model.board.Region;
import model.exceptions.IllegalActionException;
import model.player.Player;

public class AShufflePermissionCards extends Action{
	private static final int ACTIONCOST = 1;
	private Player player;
	private Region region;
	
	public AShufflePermissionCards(Player player, Region region) throws IllegalActionException{
		super(false, player);
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
