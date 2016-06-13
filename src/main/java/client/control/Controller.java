package client.control;

import java.io.Serializable;

import view.p2pdialogue.Dialogue;

@FunctionalInterface
public interface Controller extends Serializable {
	
	void parseDialogue(Dialogue dialogue);

}
