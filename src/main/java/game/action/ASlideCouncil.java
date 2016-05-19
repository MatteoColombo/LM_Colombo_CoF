package game.action;

import java.awt.Color;

import game.board.Council;
import game.board.CouncilorPool;
import game.player.Player;

public class ASlideCouncil extends Action{
	
	private static final int GAIN = 4;
	
	private Player player;
	private Color councilorColor;
	private Council council;
	private CouncilorPool pool;
	
	public ASlideCouncil(Player player, CouncilorPool pool, Council council, Color color) {
		super(true);
		this.player = player;
		this.pool = pool;
		this.council = council;
		this.councilorColor = color;
	}
	
	@Override
	public void execute() throws IllegalActionException {
		if(pool.isAvailable(councilorColor)) {
			pool.slideCouncilor(council, councilorColor);
			player.getCoins().increment(GAIN);
		} else {
			throw new IllegalActionException();
		}
	}
}
