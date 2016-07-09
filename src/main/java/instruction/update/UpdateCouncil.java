package instruction.update;

import client.model.ModelInterface;
import server.model.board.council.Council;

public class UpdateCouncil implements Update{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3997095304074407776L;
	
	private Council council;
	private int index;
	
	/**
	 * Notify to update a council in the client.
	 * By convention, the king council's number is -1
	 * @param c
	 * @param i
	 */
	public UpdateCouncil(Council c, int index) {
		this.council = c;
		this.index = index;
	}
	
	@Override
	public void execute(ModelInterface model) {
		model.setCouncil(council, index);
	}
	
}
