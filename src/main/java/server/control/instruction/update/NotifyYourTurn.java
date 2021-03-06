package server.control.instruction.update;

import client.model.ModelInterface;
/**
 * Notify the player that now is his turn
 */
public class NotifyYourTurn implements Update {

	private static final long serialVersionUID = -6910126532715632578L;

	@Override
	public void execute(ModelInterface model) {
		model.isYourTurn();
	}

}
