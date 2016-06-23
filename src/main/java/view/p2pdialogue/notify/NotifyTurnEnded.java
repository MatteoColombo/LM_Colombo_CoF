package view.p2pdialogue.notify;

import client.model.GameProperty;
import client.model.ModelInterface;
import client.view.ViewInterface;
import view.p2pdialogue.update.Update;

public class NotifyTurnEnded implements Notify, Update {

	private static final long serialVersionUID = 6750556870922810015L;

	@Override
	public void execute(ViewInterface view) {
		view.isYourTurn();
	}

	@Override
	public void execute(ModelInterface model) {
		model.yourTurnEnded();
	}

}
