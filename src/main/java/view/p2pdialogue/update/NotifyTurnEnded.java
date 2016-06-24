package view.p2pdialogue.update;

import client.model.GameProperty;
import client.model.ModelInterface;
import client.view.ViewInterface;

public class NotifyTurnEnded implements Update {

	private static final long serialVersionUID = 6750556870922810015L;

	@Override
	public void execute(ModelInterface model) {
		model.yourTurnEnded();
	}

}
