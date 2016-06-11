package client.view;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import control.Controller;
import model.player.Player;
import view.server.ClientInt;

public class RMIClientImpl implements ClientInt,Serializable{


	private static final long serialVersionUID = 1L;
	private Controller controller;
	public RMIClientImpl() throws RemoteException{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setController(Controller controller) {
		this.controller=controller;
	}

	@Override
	public void askPlayerWhatActionToDo() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void askMaxNumberOfPlayers(Integer maxNumberOfPlayers) throws IOException {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		System.out.println("prova");
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
