package view.p2pdialogue.update;

import client.model.GameProperty;
import model.board.council.Council;

public class UpdateCouncil implements Update{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3997095304074407776L;
	
	private Council council;
	private int number;
	
	/**
	 * Notify to update a council in the client.
	 * By convention, the king council's number is -1
	 * @param c
	 * @param i
	 */
	public UpdateCouncil(Council c, int i) {
		this.council = c;
		this.number = i;
	}
	
	@Override
	public void execute(GameProperty model) {
		model.getMap().setCouncil(council, number);
	}
	
}
