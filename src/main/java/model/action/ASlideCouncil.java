package model.action;

import javafx.scene.paint.Color;

import model.board.council.Council;
import model.board.council.CouncilorPool;
import model.exceptions.IllegalActionException;
import model.player.Player;

public class ASlideCouncil extends Action{
	
	private static final int GAIN = 4;
	
	private Player player;
	private Color councilorColor;
	private Council council;
	private CouncilorPool pool;
	
	public ASlideCouncil(Player player, CouncilorPool pool, Council council, Color color) throws IllegalActionException{
		super(true, player);
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
		player.getCoins().increaseAmount(GAIN);
		player.doMainAction();
	}
}
