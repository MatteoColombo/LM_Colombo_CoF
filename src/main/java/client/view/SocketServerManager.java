package client.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import client.control.Controller;
import view.p2pdialogue.Dialogue;

public class SocketServerManager extends Thread implements ServerManager{
	private Controller controller;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public SocketServerManager(Socket server, Controller controller) throws UnknownHostException, IOException {
		this.controller = controller;
		this.out = new ObjectOutputStream(server.getOutputStream());
		this.in = new ObjectInputStream(server.getInputStream());
	}

	public void publishMessage(String message) throws IOException, RemoteException {
		out.writeObject(message);
		out.flush();
	}

	public void run() {
		try {
			while (true) {
				Dialogue dialogue = (Dialogue) in.readObject();
				controller.parseDialogue(dialogue);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
