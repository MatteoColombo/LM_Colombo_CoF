package server;

import view.ClientInt;

public class RMIServer implements ServerInt{

	@Override
	public void login(ClientInt client) {
		Server.login(client);		
	}
	
}
