package server;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInt extends Remote{
	public void login(ClientInt client)  throws RemoteException; 
}
