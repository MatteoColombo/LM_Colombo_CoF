package client.cli.control;

import java.util.Scanner;

public class KeyboardListener extends Thread {

	private Scanner keyboard;
	private CliController controller;

	public KeyboardListener(CliController controller, Scanner keyboard) {
		this.keyboard = keyboard;
		this.controller = controller;
	}

	@Override
	public void run() {
		String message = "";
		while (!"quit".equals(message)) {
			message = keyboard.nextLine();
			controller.parseKeyboardMessage(message);
		}
		keyboard.close();
	}
}
