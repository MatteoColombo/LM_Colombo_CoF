package client;


import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.control.CliController;
import fx.MainApp;

public class WorkingClient {
	public static void main(String[] args) {
		PrintWriter pw = new PrintWriter(System.out);
		Scanner sc = new Scanner(System.in);
		Logger logger = Logger.getGlobal();
		int choiche = 0;
		do {
			pw.println("Choose the preferred interface:");
			pw.println("1. GUI");
			pw.println("2. CLI");
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
			MainApp view = new MainApp();
			t = new Thread(view);
			break;
		case 2:
			CliController cliControl = new CliController();
			t = new Thread(cliControl);
			break;
		default:
			MainApp def = new MainApp();
			t = new Thread(def);
			break;
		}
		t.start();
	}
}
