package view.p2pdialogue.notify;

import client.model.GameProperty;
import client.view.ViewInterface;
import view.p2pdialogue.update.Update;

public class NotifyTurnEnded implements Notify, Update {

	private static final long serialVersionUID = 6750556870922810015L;

	@Override
	public void execute(ViewInterface view) {

	}

	@Override
	public void execute(GameProperty model) {
		model.getMyPlayerData().canNotDoMainAction().set(true);
		model.getMyPlayerData().canNotDoSideAction().set(true);
	}

}
