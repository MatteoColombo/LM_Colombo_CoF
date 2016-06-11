package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

import view.server.ClientInt;
import view.server.RMIServerManagerInterface;

public interface ServerInt extends Remote{
	public void login(RMIServerManagerInterface client)  throws RemoteException; 
}
