package client.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import client.view.Cli;
import client.view.KeyboardListener;
import client.view.RMIServerManager;
import client.view.ServerManager;
import client.view.SocketServerManager;
import client.view.ViewInterface;
import server.ServerInt;
import view.p2pdialogue.Dialogue;
import view.server.RMIServerManagerInterface;

public class CliController implements Runnable, Controller {
	private Scanner keyboard;
	private ViewInterface view;
	private ServerManager serverManager;
	private KeyboardListener keyboardListener;
	private boolean canWrite;

	public CliController() {
		view = new Cli();
		keyboard = new Scanner(System.in);
		this.canWrite = false;
	}

	public void parseDialogue(Dialogue dialog) {
		dialog.execute(view);
		this.canWrite = true;
	}

	public void parseKeyboardMessage(String message) {
		if (this.canWrite) {
			try {
				serverManager.publishMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.canWrite = false;
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
				Registry registry= LocateRegistry.getRegistry(1099);
				ServerInt serv= (ServerInt)registry.lookup("ServerInt");
				RMIServerManager rmitemp=new RMIServerManager(this);
				this.serverManager=rmitemp;
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
