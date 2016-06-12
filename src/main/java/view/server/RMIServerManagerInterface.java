package view.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import view.p2pdialogue.Dialogue;

public interface RMIServerManagerInterface extends Remote {
	
	public String requestAnswer(Dialogue dialogue) throws RemoteException;
	
	public void sendNotify(Dialogue dialogue) throws RemoteException;
	
}
