package client;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.gui.control.MainApp;
import client.viewCLI.control.CliController;

public class WorkingClient {

	private WorkingClient() {
	}

	public static void main(String[] args) {
		PrintWriter pw = new PrintWriter(System.out);
		Scanner sc = new Scanner(System.in);
		Logger logger = Logger.getGlobal();
		int choiche = 0;
		do {
			pw.println("Choose the preferred interface:");
			pw.println("1. CLI");
			pw.println("2. GUI");
			pw.flush();
			try {
				choiche = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		} while (choiche != 1 && choiche != 2);
		Thread t;
		switch (choiche) {
		case 1:
			CliController cliControl;
			cliControl = new CliController(sc);
			t = new Thread(cliControl);
		break;
		case 2:
		default:
			MainApp def = new MainApp();
			t = new Thread(def);
			sc.close();
			break;
		}
		t.start();
	}
}
