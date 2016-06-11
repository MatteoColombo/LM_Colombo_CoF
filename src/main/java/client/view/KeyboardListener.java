package client.view;

import java.util.Scanner;

import client.control.CliController;

public class KeyboardListener extends Thread {

	private Scanner keyboard;
	private CliController controller;

	public KeyboardListener(CliController controller) {
		this.keyboard = new Scanner(System.in);
		this.controller = controller;
	}

	public void run() {
		String message = "";
		while (!message.equals("quit")) {
			message = keyboard.nextLine();
			controller.parseKeyboardMessage(message);
		}
	}
}
