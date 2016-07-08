package client.cli.control;

import java.util.Scanner;

public class KeyboardListener extends Thread {

	private Scanner keyboard;
	private CliController controller;

	/**
	 * Create a new Keyboard listener
	 * 
	 * @param controller
	 *            the owner
	 * @param keyboard
	 *            the scanner for the inputs
	 */
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
