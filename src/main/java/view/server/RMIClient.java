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
import view.p2pdialogue.combinedrequest.RequestMaxPlayersNumber;
import view.p2pdialogue.request.RequestPlayerName;
import view.p2pdialogue.request.RequestWhatActionToDo;

/**
 * 
 * @author Matteo Colombo
 *
 */
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
		String action = client.requestAnswer(new RequestWhatActionToDo());
		this.controller.performAction(this, action);
	}

	@Override
	public String getName(){
		return this.clientName;
	}
	
	@Override
	public void askConfiguration(List<String> maps, int maxNumberOfPlayers) throws IOException {
		// TODO Auto-generated method stub
		
	}

	private void askMaxNumberOfPlayers(Integer maxNumberOfPlayers) throws IOException {
		int max = 10;
		try{
			max = Integer.parseInt(client.requestAnswer(new RequestMaxPlayersNumber(maxNumberOfPlayers)));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		//this.controller.setMaxNumberOfPlayers(max);
	}

	private void askWichMapToUse(List<String> maps) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void askPlayerName() throws IOException {
		String name= client.requestAnswer(new RequestPlayerName());
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
