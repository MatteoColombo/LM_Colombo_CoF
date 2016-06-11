package view.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

import control.Controller;
import model.player.Player;
import view.p2pdialogue.DialogueAskMaxPlayersNumber;
import view.p2pdialogue.DialogueAskPlayerName;
import view.p2pdialogue.DialogueAskWhatActionToDo;

public class RMIClient implements ClientInt {
	private Controller controller;
	private String clientName;
	private Logger logger = Logger.getGlobal();
	private RMIServerManagerInterface client;

	public RMIClient(RMIServerManagerInterface client) {
		this.client = client;
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void askPlayerWhatActionToDo() throws IOException {
		String action = client.sendDialogue(new DialogueAskWhatActionToDo());
		this.controller.performAction(this, action);
	}

	@Override
	public String getName() throws IOException {
		return this.clientName;
	}

	@Override
	public void askMaxNumberOfPlayers(Integer maxNumberOfPlayers) throws IOException {
		int max = 10;
		try{
			max = Integer.parseInt(client.sendDialogue(new DialogueAskMaxPlayersNumber(maxNumberOfPlayers)));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		this.controller.setMaxNumberOfPlayers(max);
	}

	@Override
	public void askWichMapToUse(List<String> maps) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void askPlayerName() throws IOException {
		String name= client.sendDialogue(new DialogueAskPlayerName());
		this.clientName= name;
	}

	@Override
	public void notifyIllegalAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notifyGameLoading() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyGameStarted() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyYourTurn() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyAnotherPlayerTurn() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void askPlayerWhichMerchandiseBuy(Player buyingPlayer, List<Player> allPlayers) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean askPlayerConfirmation() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
