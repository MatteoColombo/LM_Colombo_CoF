package client.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.control.dialogue.Dialogue;

public class SocketServerManager extends Thread implements ServerManager {
	private Controller controller;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Logger logger= Logger.getGlobal();
	public SocketServerManager(Socket server, Controller controller) throws IOException {
		this.controller = controller;
		this.out = new ObjectOutputStream(server.getOutputStream());
		this.in = new ObjectInputStream(server.getInputStream());
	}

	@Override
	public void publishMessage(String message) throws IOException {
		out.writeObject(message);
		out.flush();
	}

	@Override
	public void run() {
		try {
			while (true) {
				Dialogue dialogue = (Dialogue) in.readObject();
				controller.parseDialogue(dialogue);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Connection lost, the client will terminate", e);
			} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Data corrupted, the client will terminate", e);
		}
	}

}
