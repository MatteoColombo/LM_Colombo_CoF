package client.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
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
import view.p2pdialogue.combinedrequest.CombinedDialogue;
import view.p2pdialogue.request.Request;

public class CliController implements Runnable, Controller {

	private static final long serialVersionUID = -1895721638081527089L;
	private transient Scanner keyboard;
	private transient ViewInterface view;
	private transient ServerManager serverManager;
	private transient KeyboardListener keyboardListener;
	private transient boolean canWrite;
	private transient boolean canEnqueue;
	private transient boolean waitingForKeyboard;
	private transient List<String> combinedRequestsQueue;
	private transient List<Dialogue> combinedDialogue;
	private transient Logger logger= Logger.getGlobal();
	
	public CliController() {
		view = new Cli();
		keyboard = new Scanner(System.in);
		this.canWrite = false;
		this.canEnqueue=false;
		this.waitingForKeyboard= false;
		this.combinedDialogue = new ArrayList<>();
		this.combinedRequestsQueue = new ArrayList<>();
	}

	@Override
	public synchronized void parseDialogue(Dialogue dialog) {
		if(!waitingForKeyboard){
			dialog.execute(view);
		if (dialog instanceof Request) {
			this.canWrite = true;
			this.waitingForKeyboard= true;
			this.canEnqueue = false;
		} else if (dialog instanceof CombinedDialogue) {
			this.canWrite = false;
			this.waitingForKeyboard= true;
			this.canEnqueue = true;
		} else {
			this.canWrite = false;
			this.canEnqueue = false;
		}
		}else{
			combinedDialogue.add(dialog);
		}
	}

	public void parseKeyboardMessage(String message) {
		if (this.canWrite) {
			try {
				String prec = "";
				for (String temp : this.combinedRequestsQueue)
					prec = temp + " ";
				serverManager.publishMessage(prec + message);
				this.combinedRequestsQueue.clear();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Connection lost", e);
			}
			this.canWrite = false;
			this.waitingForKeyboard= false;
			if(!combinedDialogue.isEmpty())
				parseDialogue(combinedDialogue.remove(0));

		} else if (this.canEnqueue) {
			this.combinedRequestsQueue.add(message);
			this.canEnqueue = false;
			this.waitingForKeyboard=false;
			if(!combinedDialogue.isEmpty())
				parseDialogue(combinedDialogue.remove(0));

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
