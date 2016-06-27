package server.control.dialogue.notify;

import client.control.ViewInterface;
import client.model.ModelInterface;
import server.control.dialogue.update.Update;

public class NotifyGameLoading implements Notify, Update {

	private static final long serialVersionUID = 7977501288581499309L;
	private int choosenMap;

	public NotifyGameLoading(int choosenMap) {
		this.choosenMap = choosenMap;
	}

	@Override
	public void execute(ViewInterface view) {
		view.showRoom();
	}

	@Override
	public void execute(ModelInterface model) {
		model.initMap(choosenMap);
	}
}
