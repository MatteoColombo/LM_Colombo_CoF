package server.control.connection.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.control.Controller;
import server.control.connection.ClientInt;
import server.control.dialogue.Dialogue;
import server.control.dialogue.notify.NotifyBonusFromCities;
import server.control.dialogue.notify.NotifyIllegalAction;
import server.control.dialogue.request.RequestCity;
import server.control.dialogue.request.RequestFreePermissionCard;
import server.control.dialogue.request.RequestMaxPlayersNumber;
import server.control.dialogue.request.RequestPlayerName;
import server.control.dialogue.request.RequestRewardFromPermission;
import server.control.dialogue.request.RequestWhatActionToDo;
import server.control.dialogue.request.RequestWhichItemToSell;
import server.control.dialogue.request.RequestWhichItemTouBuy;
import server.control.dialogue.request.RequestWichMapToUse;
import server.model.action.IllegalActionException;
import server.model.market.OnSaleItem;

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
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> answer = executor.submit(new Callable<String>() {
			public String call() throws RemoteException {
				String choosenAction;
				choosenAction = client.requestAnswer(new RequestWhatActionToDo());
				return choosenAction;
			}
		});
		try {
			String action = answer.get(600, TimeUnit.SECONDS);
			controller.performAction(this, action);
		} catch (TimeoutException |  ExecutionException |InterruptedException e) {
			throw new IOException(e);
		} finally {
			executor.shutdown();
		}
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
	public void notifyIllegalAction(IllegalActionException exception) {
		try {
			client.sendNotify(new NotifyIllegalAction(exception));
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public boolean isConnected() {
		/*
		 * try{ client.testConnection(); return true; }catch(IOExcetion e){
		 * logger.log(Level.SEVERE, e.getMessage(), e); }
		 */
		return true;
	}

	@Override
	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException {
		String item = client.requestAnswer(new RequestWhichItemTouBuy(itemsOnSale));
		controller.parseItemToBuy(itemsOnSale, item, this);
	}

	@Override
	public void askWichItemToSell() throws IOException {
		String item = client.requestAnswer(new RequestWhichItemToSell());
		controller.parseItemToSell(item, this);
	}

	@Override
	public void askCityToGetNobilityReward(int citiesNumber) throws IOException {
		client.sendNotify(new NotifyBonusFromCities(citiesNumber));
		List<String> cities = new ArrayList<>();
		for (int i = 0; i < citiesNumber; i++) {
			cities.add(client.requestAnswer(new RequestCity()));
		}
		controller.parseBonusGetCityBonus(cities, this);
	}

	@Override
	public void askSelectRewardOfPermissionCard() throws IOException {
		String index = client.requestAnswer(new RequestRewardFromPermission());
		controller.parseRewardOfPermissionCard(index, this);
	}

	@Override
	public void askSelectFreePermissionCard() throws IOException {
		String card = client.requestAnswer(new RequestFreePermissionCard());
		controller.parseBonusFreePermissionCard(card, this);
	}

	@Override
	public void notify(Dialogue dialog) throws IOException {
		client.sendNotify(dialog);
	}
}
