package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

import view.ClientInt;

public interface ServerInt extends Remote{
	public void login(ClientInt client)  throws RemoteException; 
}
