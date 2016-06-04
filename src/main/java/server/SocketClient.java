package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import control.Controller;

public class SocketClient implements ClientInt {
	private Controller controller;
	private Socket clientSocket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public SocketClient(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		this.out = new ObjectOutputStream(outputStream);
		this.in = new ObjectInputStream(inputStream);
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void askPlayerWhatActionToDo() throws IOException {
		out.writeObject("actionToDo");
		out.flush();
		//controller.PerformAction(this, in.readObject());
	}
}
