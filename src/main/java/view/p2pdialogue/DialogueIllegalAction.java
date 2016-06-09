package view.p2pdialogue;

import view.client.ViewInterface;

public class DialogueIllegalAction implements Dialogue{

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ViewInterface view) {
		view.printIllegalAction();		
	}

}
