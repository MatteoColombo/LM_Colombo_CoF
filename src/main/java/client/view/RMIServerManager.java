package client.view;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.control.Controller;
import view.p2pdialogue.Dialogue;
import view.server.RMIServerManagerInterface;

public class RMIServerManager extends UnicastRemoteObject implements RMIServerManagerInterface, ServerManager {

	private static final long serialVersionUID = 2758599266722476388L;
	private Controller controller;
	String message;

	public RMIServerManager(Controller controller) throws RemoteException{
		this.controller=controller;
	}

	@Override
	public String sendDialogue(Dialogue dialogue) throws RemoteException {
		this.message="";
		controller.parseDialogue(dialogue);
		synchronized (this) {
			while(this.message.equals("")){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
		
		return message;
	}

	@Override
	public void publishMessage(String message) throws IOException {
		synchronized (this) {
			this.message=message;		
			this.notifyAll();
		}	
	
	}

}
