package view.p2pdialogue.update;

import client.model.GameProperty;
import view.p2pdialogue.Dialogue;

public interface Update extends Dialogue{

	public void execute(GameProperty model);
}
