package client.control;

import instruction.Instruction;

/**
 * This is the class that needs to be implemented by the client side controllers
 *
 */
public interface Controller {
	/**
	 * call the execute method on the {@link Instruction}
	 * @param dialogue
	 */
	public void parseDialogue(Instruction dialogue);
	
	/**
	 * this method is called when the server loses the connection
	 */
	public void disconnected();
}
