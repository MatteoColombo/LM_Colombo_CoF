package client.control;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.control.connection.rmi.RMIServerManagerInterface;
import server.control.instruction.Instruction;

/**
 * This is the remote object which is sent to the server, it represents the implementation of the skeleton
 * @author Matteo Colombo
 *
 */
public class RMIServerManager extends UnicastRemoteObject implements RMIServerManagerInterface, ServerManager {

	private static final long serialVersionUID = 2758599266722476388L;
	private transient Controller controller;
	private List<String> queue;
	private transient Logger logger = Logger.getGlobal();
	private final long timestampcreation;
	/**
	 * It's the constructor
	 * 
	 * @param controller
	 *            the controller, it is used to parse the dialogues
	 * @throws RemoteException
	 */
	public RMIServerManager(Controller controller) throws RemoteException {
		this.controller = controller;
		this.queue = new ArrayList<>();
		timestampcreation= System.nanoTime();
	}

	/**
	 * This is used when the server requires and answer, the thread sleeps until
	 * a message is inserted in the queue
	 */
	@Override
	public String requestAnswer(Instruction dialogue) throws RemoteException {
		controller.parseDialogue(dialogue);
		synchronized (this) {
			while (this.queue.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					logger.log(Level.FINEST, "", e);
				}
			}

		}

		return queue.remove(0);
	}

	/**
	 * This is used when the server doesn't require an answer
	 */
	@Override
	public void sendNotify(Instruction dialogue) throws RemoteException {
		controller.parseDialogue(dialogue);
	}

	/**
	 * Adds the message to the queue of the messages that need to be sent to the
	 * server
	 */
	@Override
	public void publishMessage(String message) throws IOException {
		synchronized (this) {
			this.queue.add(message);
			this.notifyAll();
		}
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof RMIServerManager))
			return false;
		if(((RMIServerManager)obj).timestampcreation== this.timestampcreation)
			return true;
		return false;
	}
	
	 @Override
	  public int hashCode() {
	    return (int)timestampcreation;
	  }

	@Override
	public boolean testConnection() throws RemoteException {
		return true;
	}

	@Override
	public void disconnect() {
		//nothing to do with RMI!
	}

	
}
