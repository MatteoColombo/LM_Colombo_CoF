package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.Server;
import view.cli.Cli;
import view.client.ViewInterface;
import view.p2pdialogue.Dialogue;

public class Client extends Thread {
	private static ViewInterface view;
	private static Scanner keyboard;
	private static PrintWriter writer;
	private static Logger logger = Logger.getGlobal();
	private static Socket server;

	public static void main(String[] args) throws UnknownHostException, IOException {
		keyboard = new Scanner(System.in);
		writer = new PrintWriter(System.out);
		int choice = 0;
		do {
			writer.println("What type of interface do you want?");
			writer.println("1. GUI");
			writer.println("2. CLI");
			writer.flush();
			try {
				choice = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException pe) {
				logger.log(Level.WARNING, pe.getMessage(), pe);
				writer.println("Please, insert a valid number");
			}
		} while (choice != 1 && choice != 2);

		switch (choice) {
		case 1:
			break;
		case 2:
			view = new Cli();
			server = new Socket("127.0.0.1", 1994);
			ServerListener listener = new ServerListener(server, view);
			listener.start();
			new Client().start();
			break;
		default:
			break;
		}
		
	}

	public void run() {
		ObjectOutputStream objectOutputStream;
		try {
			objectOutputStream = new ObjectOutputStream(server.getOutputStream());
			while (true) {
				objectOutputStream.writeObject(keyboard.nextLine());
				objectOutputStream.flush();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static class ServerListener extends Thread {
		private Socket s;
		private ViewInterface v;

		public ServerListener(Socket s, ViewInterface v) {
			this.s = s;
			this.v = v;
		}

		public void run() {
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
				while (true) {
					Dialogue d = (Dialogue) objectInputStream.readObject();
					d.execute(v);
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
