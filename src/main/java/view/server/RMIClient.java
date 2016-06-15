package view.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.view.RMIServerManager;
import control.Controller;
import model.market.OnSaleItem;
import model.player.Player;
import view.p2pdialogue.combinedrequest.RequestMaxPlayersNumber;
import view.p2pdialogue.notify.NotifyIllegalAction;
import view.p2pdialogue.request.RequestPlayerName;
import view.p2pdialogue.request.RequestWhatActionToDo;
import view.p2pdialogue.request.RequestWichMapToUse;

public class RMIClient implements ClientInt {
	private Controller controller;
	private RMIServerManagerInterface client;
	private String clientName;
	private Logger logger = Logger.getGlobal();

	public RMIClient(RMIServerManagerInterface client) {
		this.client = client;
	}

	@Override
	public void askPlayerName() throws IOException {
		this.clientName = client.requestAnswer(new RequestPlayerName());
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void askPlayerWhatActionToDo() throws IOException {
		String action = client.requestAnswer(new RequestWhatActionToDo());
		controller.performAction(this, action);
	}

	@Override
	public String getName() {
		return clientName;
	}

	@Override
	public void askConfiguration(List<String> maps, int maxNumberOfPlayers) throws IOException {
		askMaxNumberOfPlayers(maxNumberOfPlayers);
		String config = askWichMapToUse(maps);
		controller.parseGameConfiguration(config, this);
	}

	private void askMaxNumberOfPlayers(int maxNumberOfPlayers) throws IOException {
		client.sendNotify(new RequestMaxPlayersNumber(maxNumberOfPlayers));
	}

	private String askWichMapToUse(List<String> maps) throws IOException {
		return client.requestAnswer(new RequestWichMapToUse(maps));
	}

	@Override
	public void close() {
		// TODO
	}

	@Override
	public void notifyIllegalAction() {
		try {
			client.sendNotify(new NotifyIllegalAction());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public boolean isConnected() {
		// TODO
		return true;
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
	public void sendPlayersList(List<Player> players) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyPlayerJoined(Player player) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void askWichItemToSell() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayer(Player player) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
