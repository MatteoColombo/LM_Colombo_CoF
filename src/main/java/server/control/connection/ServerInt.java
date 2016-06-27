package server.control.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.control.connection.rmi.RMIServerManagerInterface;

/**
 * This is the server interface, when a client wants to connect to the server
 * through RMI, it uses this interface with one of its implementations
 * 
 * @author Matteo Colombo
 *
 */
public interface ServerInt extends Remote {
	
	/**
	 * This is the method which is used to login to a game
	 * @param client it's a RMIServerManagerInterface object and in represents the client as a remote object
	 * @throws RemoteException
	 */
	public void login(RMIServerManagerInterface client) throws RemoteException;
}
