package view.p2pdialogue.update;

import client.model.GameProperty;
import client.model.ModelInterface;
import view.p2pdialogue.Dialogue;

public interface Update extends Dialogue{

	public void execute(ModelInterface model);
}
