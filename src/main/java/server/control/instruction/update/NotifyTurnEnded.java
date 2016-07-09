package server.control.instruction.update;

import client.model.ModelInterface;
/**
 * Notify the player that his turn is over
 */
public class NotifyTurnEnded implements Update {

	private static final long serialVersionUID = 6750556870922810015L;

	@Override
	public void execute(ModelInterface model) {
		model.yourTurnEnded();
	}

}
