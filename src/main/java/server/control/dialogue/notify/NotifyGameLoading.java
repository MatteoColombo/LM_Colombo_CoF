package server.control.dialogue.notify;

import client.control.ViewInterface;
import client.model.ModelInterface;
import server.control.dialogue.update.Update;
/**
 * Notify the game is loading, initializing the map choosen from the creator of the game,
 * and launching the waiting room screen
 */
public class NotifyGameLoading implements Notify, Update {

	private static final long serialVersionUID = 7977501288581499309L;
	private int choosenMap;

	/**
	 * create a new NotifyGameLoading
	 * @param choosenMap the index of the chosen map
	 */
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
