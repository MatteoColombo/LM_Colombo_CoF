package view;

import java.io.IOException;
import java.net.Socket;

import server.Server;

public class SocketClientConnectionHandler extends Thread {
	private Socket clientSocket;
	public SocketClientConnectionHandler(Socket clientSocket){
		this.clientSocket=clientSocket;
		this.start();
	}
	public void run(){
		try {
			ClientInt client= new SocketClient(clientSocket);
			Server.login(client);
			System.out.println("client logged");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
