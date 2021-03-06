package server.control.connection.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
 * This is the client which implements the ClientInt and is used to manage a client which connects with sockets
 * @author Matteo Colombo
 *
 */
public class SocketClient implements ClientInt {
	private Controller controller;
	private Socket clientSocket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String clientName;
	private Logger logger = Logger.getGlobal();
	private final int timeout;

	/**
	 * Create a new SocketClient
	 * @param clientSocket the Socket where the client will connect
	 * @param timeout an integer representing the time a player have to play his turn, in seconds
	 * @throws IOException
	 */
	public SocketClient(Socket clientSocket, int timeout) throws IOException {
		this.clientSocket = clientSocket;
		this.inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		this.out = new ObjectOutputStream(outputStream);
		this.in = new ObjectInputStream(inputStream);
		this.timeout=timeout;
	}

	@Override
	public void askPlayerName() throws IOException {
		out.writeObject(new RequestPlayerName());
		out.flush();
		try {
			clientName = (String) in.readObject();
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (SocketTimeoutException e) {
			clientSocket.close();
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public String getName() {
		return clientName;
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void askPlayerWhatActionToDo() throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		out.writeObject(new RequestWhatActionToDo());
		out.flush();
		Future<String> answer = executor.submit(() -> (String) in.readObject());
		try {
			String action = answer.get(timeout, TimeUnit.SECONDS);
			controller.performAction(this, action);
		} catch (TimeoutException | InterruptedException | ExecutionException e) {
			throw new IOException(e);
		} finally {
			executor.shutdown();
		}
	}

	@Override
	public void askConfiguration(int maxNumberOfPlayers) throws IOException {
		try {
			askMaxNumberOfPlayers(maxNumberOfPlayers);
			String maxPlayers = (String) in.readObject();

			askWichMapToUse();
			String choosenMap = (String) in.readObject();

			controller.parseGameConfiguration(maxPlayers, choosenMap, this);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void askMaxNumberOfPlayers(int maxNumberOfPlayers) throws IOException {
		out.writeObject(new RequestMaxPlayersNumber(maxNumberOfPlayers));
		out.flush();
	}

	private void askWichMapToUse() throws IOException {
		out.writeObject(new RequestWichMapToUse());
		out.flush();
	}

	@Override
	public void close() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void notifyIllegalAction(IllegalActionException exception) {
		try {
			out.writeObject(new NotifyIllegalAction(exception));
			out.flush();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public boolean isConnected() {
		return clientSocket.isConnected();
	}

	@Override
	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException {
		List<OnSaleItem> clone= new ArrayList<>();
		for(OnSaleItem item: itemsOnSale)
			clone.add(item.newCopy());
		out.writeObject(new RequestWhichItemToBuy(clone));
		out.flush();
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> answer = executor.submit(() -> (String) in.readObject());
		try {
			String item = answer.get(timeout, TimeUnit.SECONDS);
			controller.parseItemToBuy(itemsOnSale, item, this);
		} catch (TimeoutException | InterruptedException | ExecutionException e) {
			throw new IOException(e);
		} finally {
			executor.shutdown();
		}
	}

	@Override
	public void askWichItemToSell() throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		out.writeObject(new RequestWhichItemToSell());
		out.flush();
		Future<String> answer = executor.submit(() -> (String) in.readObject());
		try {
			String item = answer.get(timeout, TimeUnit.SECONDS);
			controller.parseItemToSell(item, this);
		} catch (TimeoutException | InterruptedException | ExecutionException e) {
			throw new IOException(e);
		} finally {
			executor.shutdown();
		}
	}

	@Override
	public void askCityToGetNobilityReward(int citiesNumber) throws IOException {

		List<String> cities = new ArrayList<>();
		for (int i = 0; i < citiesNumber; i++) {
			out.writeObject(new RequestCity(citiesNumber));
			out.flush();
			try {
				cities.add((String) in.readObject());
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		controller.parseBonusGetCityBonus(cities, this);
	}

	@Override
	public void askSelectRewardOfPermissionCard() throws IOException {
		out.writeObject(new RequestRewardFromPermission());
		out.flush();
		try {
			String index = (String) in.readObject();
			controller.parseRewardOfPermissionCard(index, this);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void askSelectFreePermissionCard() throws IOException {
		out.writeObject(new RequestFreePermissionCard());
		out.flush();
		try {
			String card = (String) in.readObject();
			controller.parseBonusFreePermissionCard(card, this);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void notify(Instruction dialog) throws IOException {
		out.writeObject(dialog);
		out.flush();
	}

	@Override
	public void askConfigurationMethod() throws IOException {
		out.writeObject(new RequestConfigurationMethod());
		out.flush();
		try {
			String answer = (String) in.readObject();
			controller.parseConfigurationType(answer);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
