package view.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.view.RMIServerManager;
import control.Controller;
import model.market.OnSaleItem;
import model.player.Player;
import view.p2pdialogue.notify.NotifyGameLoading;
import view.p2pdialogue.notify.NotifyGameStarted;
import view.p2pdialogue.notify.NotifyIllegalAction;
import view.p2pdialogue.notify.NotifyPlayerJoined;
import view.p2pdialogue.notify.NotifyPlayersList;
import view.p2pdialogue.notify.NotifyUpdatePlayer;
import view.p2pdialogue.notify.NotifyYourTurn;
import view.p2pdialogue.request.RequestMaxPlayersNumber;
import view.p2pdialogue.request.RequestPlayerName;
import view.p2pdialogue.request.RequestWhatActionToDo;
import view.p2pdialogue.request.RequestWhichItemToSell;
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
	public void askConfiguration(int maxNumberOfPlayers) throws IOException {
		String players = askMaxNumberOfPlayers(maxNumberOfPlayers);
		String map = askWichMapToUse();
		controller.parseGameConfiguration(players, map, this);
	}

	private String askMaxNumberOfPlayers(int maxNumberOfPlayers) throws IOException {
		return client.requestAnswer(new RequestMaxPlayersNumber(maxNumberOfPlayers));
	}

	private String askWichMapToUse() throws IOException {
		return client.requestAnswer(new RequestWichMapToUse());
	}

	@Override
	public void close() {
		
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
		/*try{
			client.testConnection();
			return true;
		}catch(IOExcetion e){
			logger.log(Level.SEVERE, e.getMessage(), e);
		}*/
		return true;
	}

	@Override
	public void notifyGameLoading() throws IOException {
		client.sendNotify(new NotifyGameLoading());
	}

	@Override
	public void notifyGameStarted() throws IOException {
		client.sendNotify(new NotifyGameStarted());
	}

	@Override
	public void notifyYourTurn() throws IOException {
		client.sendNotify(new NotifyYourTurn());
	}

	@Override
	public void notifyAnotherPlayerTurn() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendPlayersList(List<Player> players) throws IOException {
		client.sendNotify(new NotifyPlayersList(players));
	}

	@Override
	public void notifyPlayerJoined(Player player) throws IOException {
		client.sendNotify(new NotifyPlayerJoined(player));
	}

	@Override
	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void askWichItemToSell() throws IOException {
		String item=client.requestAnswer(new RequestWhichItemToSell());			
		controller.parseItemToSell(item, this);
	}


	@Override
	public void updatePlayer(Player player, int index) throws IOException {
		client.sendNotify(new NotifyUpdatePlayer(player, index));
	}
}
