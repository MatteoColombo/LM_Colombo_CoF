package server;
import java.rmi.Remote;

public interface ServerInt extends Remote{
	public void login(ClientInt client);
}
