package client.control;

import java.io.Serializable;

import server.control.dialogue.Dialogue;

@FunctionalInterface
public interface Controller extends Serializable {
	
	void parseDialogue(Dialogue dialogue);

}
