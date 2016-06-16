package view.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import control.Controller;
import model.market.OnSaleItem;
import model.player.Player;
import view.p2pdialogue.combinedrequest.RequestMaxPlayersNumber;
import view.p2pdialogue.notify.NotifyGameLoading;
import view.p2pdialogue.notify.NotifyGameStarted;
import view.p2pdialogue.notify.NotifyIllegalAction;
import view.p2pdialogue.notify.NotifyPlayerJoined;
import view.p2pdialogue.notify.NotifyPlayersList;
import view.p2pdialogue.notify.NotifyUpdatePlayer;
import view.p2pdialogue.notify.NotifyYourTurn;
import view.p2pdialogue.request.RequestPlayerName;
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
		this.clientSocket.setSoTimeout(60000);
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
		out.writeObject(new RequestWhatActionToDo());
		out.flush();
		String action = "";
		try {
			action = (String) in.readObject();
			controller.performAction(this, action);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public String getName() {
		return clientName;
	}

	@Override
	public void askConfiguration(List<String> maps, int maxNumberOfPlayers) throws IOException {
		askMaxNumberOfPlayers(maxNumberOfPlayers);
		askWichMapToUse(maps);
		try {
			String returnMessage = (String) in.readObject();
			System.out.println(returnMessage);
			controller.parseGameConfiguration(returnMessage, this);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void askMaxNumberOfPlayers(int maxNumberOfPlayers) throws IOException {
		out.writeObject(new RequestMaxPlayersNumber(maxNumberOfPlayers));
		out.flush();
	}

	private void askWichMapToUse(List<String> maps) throws IOException {
		out.writeObject(new RequestWichMapToUse(maps));
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
	public void notifyIllegalAction() {
		try {
			out.writeObject(new NotifyIllegalAction());
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
	public void notifyGameLoading() throws IOException {
		out.writeObject(new NotifyGameLoading());
		out.flush();
	}

	@Override
	public void notifyGameStarted() throws IOException {
		out.writeObject(new NotifyGameStarted());
		out.flush();
	}

	@Override
	public void notifyYourTurn() throws IOException {
		out.writeObject(new NotifyYourTurn());
		out.flush();
	}

	@Override
	public void notifyAnotherPlayerTurn() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendPlayersList(List<Player> players) throws IOException {
		out.writeObject(new NotifyPlayersList(players));
		out.flush();
	}

	@Override
	public void notifyPlayerJoined(Player player) throws IOException {
		out.writeObject(new NotifyPlayerJoined(player));
		out.flush();		
	}

	@Override
	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException {
		
	}

	@Override
	public void askWichItemToSell() throws IOException {
		out.writeObject(new RequestWhichItemToSell());
		out.flush();
		try{
			String item=(String)in.readObject();
			
		}catch(ClassNotFoundException e){
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void updatePlayer(Player player, int index) throws IOException {
		out.writeObject(new NotifyUpdatePlayer(player, index));
		out.flush();
	}
}
