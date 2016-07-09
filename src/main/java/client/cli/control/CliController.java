package client.cli.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner; 
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import client.cli.model.Game;
import client.cli.view.Cli;
import client.control.Controller;
import client.control.RMIServerManager;
import client.control.ServerManager;
import client.control.SocketServerManager;
import server.control.connection.ServerInt;
import server.control.instruction.Instruction;
import server.control.instruction.notify.Notify;
import server.control.instruction.request.Request;
import server.control.instruction.update.Update;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;

/**
 * This is the MVC controller on the client 
 *
 */
public class CliController implements Runnable, Controller {
	private Scanner keyboard;
	private Cli view;
	private ServerManager serverManager;
	private KeyboardListener keyboardListener;
	private boolean canWrite;
	private Logger logger;
	private Game model;
	private Configuration config;
	
	/**
	 * Creates a CliController with the given scanner to read the user input
	 * @param sc the scanner
	 */
	public CliController(Scanner sc) {
		logger= Logger.getLogger(CliController.class);
		PropertyConfigurator.configure("src/main/resources/logger");
		try {
			config= new Configuration();
			view = new Cli(config);
			this.model = new Game(view);
			this.view.setModel(model);
			this.model.setConfiguration(config);
			keyboard = sc;
			this.canWrite = false;
		} catch (ConfigurationErrorException e) {
			logger.error(e);
			view.printMessage("There is an error with the configuration file!");
		}	
	}
	/**
	 * execute a {@link Instruction}
	 * @param dialog the given Dialogue
	 */
	@Override
	public synchronized void parseDialogue(Instruction dialog) {
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
		if("quit".equals(message)){
			serverManager.disconnect();
			return;
		}
		if (this.canWrite) {
			try {
				serverManager.publishMessage(message);
			} catch (IOException e) {
				logger.error(e);
				view.printMessage("Connection lost, the client will terminate");
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
				logger.error(e);
				view.printMessage("Error, you didn't insert a number");
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
			logger.error(e);
			disconnected();
		}
	}
	
	@Override
	public void disconnected() {
		view.printMessage("Connection lost, the client will terminate");
		System.exit(0);
	}
}
