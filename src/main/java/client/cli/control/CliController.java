package client.cli.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.cli.model.Game;
import client.cli.view.Cli;
import client.control.Controller;
import client.control.RMIServerManager;
import client.control.ServerManager;
import client.control.SocketServerManager;
import server.control.connection.ServerInt;
import server.control.dialogue.Dialogue;
import server.control.dialogue.notify.Notify;
import server.control.dialogue.request.Request;
import server.control.dialogue.update.Update;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;

public class CliController implements Runnable, Controller {
	private Scanner keyboard;
	private Cli view;
	private ServerManager serverManager;
	private KeyboardListener keyboardListener;
	private boolean canWrite;
	private Logger logger = Logger.getGlobal();
	private Game model;
	private Configuration config;
	
	/**
	 * Creates a CliController with the given scanner to read the user input
	 * @param sc the scanner
	 */
	public CliController(Scanner sc) {
		try {
			config= new Configuration();
			view = new Cli(config);
			this.model = new Game(view);
			this.view.setModel(model);
			this.model.setConfiguration(config);
			keyboard = sc;
			this.canWrite = false;
		} catch (ConfigurationErrorException e) {
			logger.log(Level.SEVERE, "There is an error with the configuration file!", e);
		}	
	}
	/**
	 * execute a {@link Dialogue}
	 * @param dialog the given Dialogue
	 */
	@Override
	public synchronized void parseDialogue(Dialogue dialog) {
		if (dialog instanceof Request) {
			this.canWrite = true;
			((Request) dialog).execute(view);
		}
		if (dialog instanceof Notify) {
			this.canWrite = false;
			((Notify) dialog).execute(view);
		}
		if (dialog instanceof Update) {
			((Update) dialog).execute(model);
			if (dialog instanceof Request)
				this.canWrite = true;
			else
				this.canWrite = false;
		}
	}
	/**
	 * send a message to the serverManager
	 * @param message the received message
	 */
	public void parseKeyboardMessage(String message) {
		if (this.canWrite) {
			try {
				serverManager.publishMessage(message);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Connection lost, the client will terminate", e);
				return;
			}

			this.canWrite = false;
		}
	}
	/**
	 * start the Controller for the cli
	 */
	@Override
	public void run() {
		view.showInitMenu();
		int connectionType = 0;
		do {
			try {
				view.showGetConnectionType();
				connectionType = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {
				logger.log(Level.SEVERE, "Error, you didn't insert a number",e);
			}
		} while (connectionType != 1 && connectionType != 2);
		try {
			keyboardListener = new KeyboardListener(this, keyboard);
			keyboardListener.start();
			switch (connectionType) {
			case 1:
				Socket server = new Socket(model.getConfiguration().getServerAddress(), model.getConfiguration().getSocketPort());
				SocketServerManager sockettemp = new SocketServerManager(server, this);
				sockettemp.start();
				this.serverManager = sockettemp;
				break;
			case 2:
				ServerInt serv = (ServerInt) LocateRegistry.getRegistry(model.getConfiguration().getServerAddress(), model.getConfiguration().getRmiPort()).lookup("ServerInt");
				RMIServerManager rmitemp = new RMIServerManager(this);
				this.serverManager = rmitemp;
				serv.login(rmitemp);
				break;
			default:
				break;
			}

		} catch (IOException | NotBoundException e) {
			logger.log(Level.SEVERE, "Connection problem, the application will terminate", e);
			System.exit(1);
		}
	}
}
