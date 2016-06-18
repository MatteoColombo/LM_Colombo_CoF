package client.view;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.control.Controller;
import view.p2pdialogue.Dialogue;
import view.server.RMIServerManagerInterface;

public class RMIServerManager extends UnicastRemoteObject implements RMIServerManagerInterface, ServerManager {

	private static final long serialVersionUID = 2758599266722476388L;
	private Controller controller;
	private List<String> queue;
	private transient Logger logger = Logger.getGlobal();

	public RMIServerManager(Controller controller) throws RemoteException {
		this.controller = controller;
		this.queue = new ArrayList<>();
	}

	@Override
	public String requestAnswer(Dialogue dialogue) throws RemoteException {
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

	@Override
	public void sendNotify(Dialogue dialogue) throws RemoteException {
		controller.parseDialogue(dialogue);
	}

	@Override
	public void publishMessage(String message) throws IOException {
		synchronized (this) {
			this.queue.add(message);
			this.notifyAll();
		}
	}

}
