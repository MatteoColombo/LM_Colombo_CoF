package client.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.view.Cli;
import client.view.KeyboardListener;
import client.view.RMIServerManager;
import client.view.ServerManager;
import client.view.SocketServerManager;
import client.view.ViewInterface;
import server.ServerInt;
import view.p2pdialogue.Dialogue;
import view.p2pdialogue.notify.Notify;
import view.p2pdialogue.request.Request;

public class CliController implements Runnable, Controller {

	private static final long serialVersionUID = -1895721638081527089L;
	private transient Scanner keyboard;
	private transient ViewInterface view;
	private transient ServerManager serverManager;
	private transient KeyboardListener keyboardListener;
	private transient boolean canWrite;
	private transient Logger logger = Logger.getGlobal();

	public CliController() {
		view = new Cli();
		keyboard = new Scanner(System.in);
		this.canWrite = false;
	}

	@Override
	public synchronized void parseDialogue(Dialogue dialog) {
		if (dialog instanceof Request) {
			this.canWrite = true;
		} else if (dialog instanceof Notify) {
			this.canWrite = false;
		}
		dialog.execute(view);
	}

	public void parseKeyboardMessage(String message) {
		if (this.canWrite) {
			try {
				serverManager.publishMessage(message);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Connection lost, the client will terminate", e);
			}

			this.canWrite = false;
		}
	}

	@Override
	public void run() {
		view.showInitMenu();
		int connectionType = 0;
		do {
			try {
				view.showGetConnectionType();
				connectionType = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {
				logger.log(Level.SEVERE, "Error, you didn't insert a number", e);
			}
		} while (connectionType != 1 && connectionType != 2);
		try {
			keyboardListener = new KeyboardListener(this);
			keyboardListener.start();
			switch (connectionType) {
			case 1:
				Socket server = new Socket("localhost", 1994);
				SocketServerManager sockettemp = new SocketServerManager(server, this);
				sockettemp.start();
				this.serverManager = sockettemp;
				break;
			case 2:
				ServerInt serv = (ServerInt) LocateRegistry.getRegistry(1099).lookup("ServerInt");
				RMIServerManager rmitemp = new RMIServerManager(this);
				this.serverManager = rmitemp;
				serv.login(rmitemp);
				break;
			default:
				break;
			}

		} catch (IOException | NotBoundException e) {
			logger.log(Level.SEVERE, "Connection problem, the application will terminate", e);
		}
	}
}
