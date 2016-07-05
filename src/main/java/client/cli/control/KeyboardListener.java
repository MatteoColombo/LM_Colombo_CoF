package client.cli.control;

import java.util.Scanner;

public class KeyboardListener extends Thread {

	private Scanner keyboard;
	private CliController controller;
	private boolean close;

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
		close = false;
	}

	@Override
	public void run() {
		String message = "";
		while (!"quit".equals(message)) {
			message = keyboard.nextLine();
			if(!close)
				controller.parseKeyboardMessage(message);
		}
		keyboard.close();
	}

	public void setStop() {
		this.close=true;
		this.keyboard.close();
	}
}
