package server.control.connection.rmi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import server.control.instruction.Instruction;
import server.control.instruction.notify.NotifyIllegalAction;
import server.control.instruction.request.RequestCity;
import server.control.instruction.request.RequestConfigurationMethod;
import server.control.instruction.request.RequestFreePermissionCard;
import server.control.instruction.request.RequestMaxPlayersNumber;
import server.control.instruction.request.RequestPlayerName;
import server.control.instruction.request.RequestRewardFromPermission;
import server.control.instruction.request.RequestWhatActionToDo;
import server.control.instruction.request.RequestWhichItemToBuy;
import server.control.instruction.request.RequestWhichItemToSell;
import server.control.instruction.request.RequestWichMapToUse;
import server.model.action.IllegalActionException;
import server.model.market.OnSaleItem;

/**
 * This is the RMI Client, it extends the ClientInt and it is used when the client connects through RMI
 * @author Matteo Colombo
 *
 */
public class RMIClient implements ClientInt {
	private Controller controller;
	private RMIServerManagerInterface client;
	private String clientName;
	private Logger logger = Logger.getGlobal();
	private final int timeout;
	
	/**
	 * Saves the client which is the remote object of the client
	 * @param client
	 */
	public RMIClient(RMIServerManagerInterface client, int timeout) {
		this.client = client;
		this.timeout=timeout;
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
		Future<String> answer = executor.submit(() -> client.requestAnswer(new RequestWhatActionToDo()));
		try {
			String action = answer.get(timeout, TimeUnit.SECONDS);
			controller.performAction(this, action);
		} catch (TimeoutException | ExecutionException | InterruptedException e) {
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

	/**
	 * Private method which is used to ask the max number of players
	 * @param maxNumberOfPlayers
	 * @return
	 * @throws IOException
	 */
	private String askMaxNumberOfPlayers(int maxNumberOfPlayers) throws IOException {
		return client.requestAnswer(new RequestMaxPlayersNumber(maxNumberOfPlayers));
	}

	/**
	 * Private method which is used to ask the game map
	 * @return
	 * @throws IOException
	 */
	private String askWichMapToUse() throws IOException {
		return client.requestAnswer(new RequestWichMapToUse());
	}

	@Override
	public void close() {
		this.client = null;
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
		try {
			return client.testConnection();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}

	@Override
	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> answer = executor.submit(() -> client.requestAnswer(new RequestWhichItemToBuy(itemsOnSale)));
		try {
			String item = answer.get(timeout, TimeUnit.SECONDS);
			controller.parseItemToBuy(itemsOnSale, item, this);
		} catch (TimeoutException | ExecutionException | InterruptedException e) {
			throw new IOException(e);
		} finally {
			executor.shutdown();
		}
	}

	@Override
	public void askWichItemToSell() throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> answer = executor.submit(() -> client.requestAnswer(new RequestWhichItemToSell()));
		try {
			String item = answer.get(timeout, TimeUnit.SECONDS);
			controller.parseItemToSell(item, this);
		} catch (TimeoutException | ExecutionException | InterruptedException e) {
			throw new IOException(e);
		} finally {
			executor.shutdown();
		}
		
	}

	@Override
	public void askCityToGetNobilityReward(int citiesNumber) throws IOException {
		List<String> cities = new ArrayList<>();
		for (int i = 0; i < citiesNumber; i++) {
			cities.add(client.requestAnswer(new RequestCity(citiesNumber)));
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
	public void notify(Instruction dialog) throws IOException {
		client.sendNotify(dialog);
	}

	@Override
	public void askConfigurationMethod() throws IOException {
		String answer = client.requestAnswer(new RequestConfigurationMethod());
		controller.parseConfigurationType(answer);
	}

}
