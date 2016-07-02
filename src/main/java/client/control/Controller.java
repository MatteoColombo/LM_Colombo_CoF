package client.control;

import server.control.dialogue.Dialogue;

@FunctionalInterface
public interface Controller {
	/**
	 * call the execute method on the {@link Dialogue}
	 * @param dialogue
	 */
	void parseDialogue(Dialogue dialogue);
}
