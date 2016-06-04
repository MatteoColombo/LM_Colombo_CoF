package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import control.Controller;

public class SocketClient implements ClientInt{
	private Controller controller;
	private Socket clientSocket;
	private InputStream input;
	private OutputStream output;
	public SocketClient(Socket clientSocket) throws IOException{
		this.clientSocket=clientSocket;
		this.input= clientSocket.getInputStream();
		this.output= clientSocket.getOutputStream();
	}
	@Override
	public void setController(Controller controller) {
		this.controller= controller;		
	}
}
