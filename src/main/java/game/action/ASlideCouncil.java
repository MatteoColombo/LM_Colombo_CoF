package game.action;

import java.awt.Color;

import game.board.council.Council;
import game.board.council.CouncilorPool;
import game.exceptions.IllegalActionException;
import game.player.Player;

public class ASlideCouncil extends Action{
	
	private static final int GAIN = 4;
	
	private Player player;
	private Color councilorColor;
	private Council council;
	private CouncilorPool pool;
	
	public ASlideCouncil(Player player, CouncilorPool pool, Council council, Color color) throws IllegalActionException{
		super(true);
		this.player = player;
		this.pool = pool;
		this.council = council;
		this.councilorColor = color;
		if(!pool.isAvailable(councilorColor)) {
			throw new IllegalActionException("there are no more councilor available of the choosen color");
		}
	}
	
	@Override
	public void execute() {
		pool.slideCouncilor(council, councilorColor);
		player.getCoins().increment(GAIN);
	}
}
