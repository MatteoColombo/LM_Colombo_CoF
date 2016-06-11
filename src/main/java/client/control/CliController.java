package client.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

import client.view.Cli;
import client.view.KeyboardListener;
import client.view.ServerManager;
import client.view.SocketServerManager;
import client.view.ViewInterface;
import view.p2pdialogue.Dialogue;

public class CliController implements Runnable {
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
			switch (connectionType) {
			case 1:
				Socket server = new Socket("localhost", 1994);
				SocketServerManager temp = new SocketServerManager(server, this);
				temp.start();
				this.serverManager = temp;
				break;
			case 2:

			default:
				break;
			}

			keyboardListener = new KeyboardListener(this);
			keyboardListener.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}