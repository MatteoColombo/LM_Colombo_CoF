package client.control;

import server.control.dialogue.Dialogue;

@FunctionalInterface
public interface Controller {
	void parseDialogue(Dialogue dialogue);
}
