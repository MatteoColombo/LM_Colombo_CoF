package server.control.connection.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import instruction.Instruction;

/**
 * This class represents the client in an RMI connection and it is the remote object on which the server works
 * @author Matteo Colombo
 *
 */
public interface RMIServerManagerInterface extends Remote {
	
	/**
	 * This method is used when the server needs a reply from the client
	 * @param dialogue the Dialogue object which will update the client's UI
	 * @return a String, the answer
	 * @throws RemoteException
	 */
	public String requestAnswer(Instruction dialogue) throws RemoteException;
	
	/**
	 * This method is used when the server doesn't need a reply but it just want to notify the client about something
	 * @param dialogue the Dialogue object which will update the client's UI
	 * @throws RemoteException
	 */
	public void sendNotify(Instruction dialogue) throws RemoteException;
	
	/**
	 * This is used to check if the client is connected
	 * @return true if it is connected, false otherwise
	 * @throws RemoteException
	 */
	public boolean testConnection() throws RemoteException;
}
