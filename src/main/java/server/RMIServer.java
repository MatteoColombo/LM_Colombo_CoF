package server;

import view.server.ClientInt;

public class RMIServer implements ServerInt{

	@Override
	public void login(ClientInt client) {
		Server.login(client);		
	}
	
}
