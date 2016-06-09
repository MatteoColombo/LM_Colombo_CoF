package view;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import control.Controller;

public class SocketClient implements ClientInt {
	private Controller controller;
	private Socket clientSocket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String clientName;
	private Logger logger = Logger.getGlobal();

	public SocketClient(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		this.out = new ObjectOutputStream(outputStream);
		this.in = new ObjectInputStream(inputStream);
	}

	@Override
	public void askPlayerName() throws IOException {
		out.writeObject("name");
		out.flush();
		try {
			clientName = (String) in.readObject();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(),e);
		}
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void askPlayerWhatActionToDo() throws IOException {
		out.writeObject("actionToDo");
		out.flush();
		String action = "";
		try {
			action = (String) in.readObject();
			controller.performAction(this, action);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(),e);
		}
	}

	@Override
	public String getName() {
		return clientName;
	}

	@Override
	public void askMaxNumberOfPlayers(Integer maxNumberOfPlayers) throws IOException {
		int maxNumber = 10;
		out.writeObject("maxplayer");
		out.flush();
		try {
			maxNumber = (Integer) in.readObject();
			controller.setMaxNumberOfPlayers(maxNumber);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(),e);
		}
	}

	@Override
	public void askWichMapToUse(List<String> maps) throws IOException {
		int map = 0;
		out.writeObject(maps);
		out.flush();
		try {
			map = (Integer) in.readObject();
			controller.setChoosenMap(map);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(),e);
		}
	}

	@Override
	public void close() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(),e);
		}
	}
}
