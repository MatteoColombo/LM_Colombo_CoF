package server.control.instruction.update;

import client.model.ModelInterface;


public class NotifyTurnEnded implements Update {

	private static final long serialVersionUID = 6750556870922810015L;

	@Override
	public void execute(ModelInterface model) {
		model.yourTurnEnded();
	}

}
