package view.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
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

import control.Controller;
import model.exceptions.IllegalActionException;
import model.market.OnSaleItem;
import view.p2pdialogue.Dialogue;
import view.p2pdialogue.notify.NotifyBonusFromCities;
import view.p2pdialogue.notify.NotifyIllegalAction;
import view.p2pdialogue.request.RequestCity;
import view.p2pdialogue.request.RequestFreePermissionCard;
import view.p2pdialogue.request.RequestMaxPlayersNumber;
import view.p2pdialogue.request.RequestPlayerName;
import view.p2pdialogue.request.RequestRewardFromPermission;
import view.p2pdialogue.request.RequestWhatActionToDo;
import view.p2pdialogue.request.RequestWhichItemToSell;
import view.p2pdialogue.request.RequestWichMapToUse;

public class SocketClient implements ClientInt {
	private Controller controller;
	private Socket clientSocket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String clientName;
	private Logger logger = Logger.getGlobal();

	public SocketClient(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		this.out = new ObjectOutputStream(outputStream);
		this.in = new ObjectInputStream(inputStream);
		// this.clientSocket.setSoTimeout(60000);
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
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void askPlayerWhatActionToDo() throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		out.writeObject(new RequestWhatActionToDo());
		out.flush();
		Future<String> answer = executor.submit(new Callable<String>() {
			public String call() throws ClassNotFoundException, IOException {
				String choosenAction;
				choosenAction = (String) in.readObject();
				return choosenAction;
			}
		});
		try {
			String action = answer.get(60, TimeUnit.SECONDS);
			controller.performAction(this, action);
		} catch (TimeoutException | InterruptedException | ExecutionException e) {
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

	}

	@Override
	public void askWichItemToSell() throws IOException {
		out.writeObject(new RequestWhichItemToSell());
		out.flush();
		try {
			String item = (String) in.readObject();
			controller.parseItemToSell(item, this);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void askCityToGetNobilityReward(int citiesNumber) throws IOException {
		out.writeObject(new NotifyBonusFromCities(citiesNumber));
		out.flush();
		List<String> cities = new ArrayList<>();
		for (int i = 0; i < citiesNumber; i++) {
			out.writeObject(new RequestCity());
			out.flush();
			try {
				cities.add((String) in.readObject());
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
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
	public void notify(Dialogue dialog) throws IOException {
		out.writeObject(dialog);
		out.flush();
	}
}
