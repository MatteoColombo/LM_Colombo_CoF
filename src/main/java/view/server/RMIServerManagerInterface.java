package view.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import view.p2pdialogue.Dialogue;

public interface RMIServerManagerInterface extends Remote {
	
	public String sendDialogue(Dialogue dialogue) throws RemoteException;
	
	
}
