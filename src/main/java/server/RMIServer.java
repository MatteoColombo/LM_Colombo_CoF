package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.view.ViewInterface;
import view.p2pdialogue.Dialogue;
import view.p2pdialogue.request.RequestPlayerName;
import view.server.ClientInt;
import view.server.RMIClient;
import view.server.RMIServerManagerInterface;

public class RMIServer extends UnicastRemoteObject implements ServerInt{

	private static final long serialVersionUID = 1L;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
