package server.control.instruction.request;


import client.control.ViewInterface;
import server.control.instruction.Instruction;

/**
 * This is the dialogue which is sent when a response from the client is requested
 * When a request is sent, the client must send the asked informations
 * @author Matteo Colombo
 *
 */
@FunctionalInterface
public interface Request extends Instruction {
	/**
	 * This is the method which, when called by the client-side controller,
	 * updates the UI
	 * 
	 * @param view the client side View
	 */
	public void execute(ViewInterface view);
}
