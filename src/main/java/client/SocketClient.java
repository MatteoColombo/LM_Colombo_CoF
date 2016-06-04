package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	public SocketClient() throws UnknownHostException, IOException{
		Socket s= new Socket("127.0.0.1",1994);
		
	}
	
}

