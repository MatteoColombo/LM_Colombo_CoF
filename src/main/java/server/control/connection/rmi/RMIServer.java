package server.control.connection.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.Server;
import server.control.connection.ClientInt;
import server.control.connection.ServerInt;

public class RMIServer extends UnicastRemoteObject implements ServerInt{

	private static final long serialVersionUID = 1L;
	private transient Logger logger=Logger.getGlobal();
	public RMIServer() throws RemoteException {
		super();
	}

	@Override
	public void login(RMIServerManagerInterface client) {
		try {
			ClientInt rmiclient= new RMIClient(client);
			rmiclient.askPlayerName();
			Server.login(rmiclient);		
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
}
