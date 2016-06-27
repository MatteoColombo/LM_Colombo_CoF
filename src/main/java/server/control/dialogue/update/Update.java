package server.control.dialogue.update;

import client.model.ModelInterface;
import server.control.dialogue.Dialogue;

public interface Update extends Dialogue{

	public void execute(ModelInterface model);
}
