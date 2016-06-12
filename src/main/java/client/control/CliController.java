package client.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	private Scanner keyboard;
	private ViewInterface view;
	private ServerManager serverManager;
	private KeyboardListener keyboardListener;
	private boolean canWrite;
	private boolean canEnqueue;
	private boolean waitingForKeyboard;
	private List<String> combinedRequestsQueue;
	private List<Dialogue> combinedDialogue;

	public CliController() {
		view = new Cli();
		keyboard = new Scanner(System.in);
		this.canWrite = false;
		this.canEnqueue=false;
		this.waitingForKeyboard= false;
		this.combinedDialogue = new ArrayList<>();
		this.combinedRequestsQueue = new ArrayList<>();
	}

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
				e.printStackTrace();
			}
			this.canWrite = false;
			this.waitingForKeyboard= false;
			if(combinedDialogue.size()>0)
				parseDialogue(combinedDialogue.remove(0));

		} else if (this.canEnqueue) {
			this.combinedRequestsQueue.add(message);
			this.canEnqueue = false;
			this.waitingForKeyboard=false;
			if(combinedDialogue.size()>0)
				parseDialogue(combinedDialogue.remove(0));

		}
		
	}

	public void run() {
		view.showInitMenu();
		int connectionType = 0;
		do {
			try {
				view.showGetConnectionType();
				connectionType = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {

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
				Registry registry = LocateRegistry.getRegistry(1099);
				ServerInt serv = (ServerInt) registry.lookup("ServerInt");
				RMIServerManager rmitemp = new RMIServerManager(this);
				this.serverManager = rmitemp;
				serv.login(rmitemp);
				break;
			default:
				break;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
