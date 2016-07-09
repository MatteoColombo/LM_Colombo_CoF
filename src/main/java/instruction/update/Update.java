package instruction.update;

import client.model.ModelInterface;
import instruction.Instruction;

/**
 * This is the Dialogue which is sent when a model update is needed
 * @author Matteo Colombo
 *
 */
@FunctionalInterface
public interface Update extends Instruction{

	/**
	 * This is the method called when a model update is needed
	 * @param model it's the model on the client
	 */
	public void execute(ModelInterface model);
}
