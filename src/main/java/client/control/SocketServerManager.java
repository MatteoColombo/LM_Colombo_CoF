package client.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import org.apache.log4j.*;

import server.control.dialogue.Dialogue;

public class SocketServerManager extends Thread implements ServerManager {
	private Controller controller;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Logger logger;
	private Socket server;
	public SocketServerManager(Socket server, Controller controller) throws IOException {
		logger= Logger.getLogger(SocketServerManager.class);
		PropertyConfigurator.configure("src/main/resources/logger");
		this.controller = controller;
		this.server=server;
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
			logger.error(e);
			controller.disconnected();
			} catch (ClassNotFoundException e) {
			logger.error(e);
		} 
	}

	@Override
	public void disconnect() {
		try {
			if(server.isConnected()){
			out.close();
			in.close();
			server.close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
