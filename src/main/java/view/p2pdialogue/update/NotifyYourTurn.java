package view.p2pdialogue.update;

import client.model.ModelInterface;

public class NotifyYourTurn implements Update {

	private static final long serialVersionUID = -6910126532715632578L;

	@Override
	public void execute(ModelInterface model) {
		model.isYourTurn();
	}

}
