package view;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import control.Controller;

public class SocketClient implements ClientInt {
	private Controller controller;
	private Socket clientSocket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String clientName;
	
	public SocketClient(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		this.out = new ObjectOutputStream(outputStream);
		this.in = new ObjectInputStream(inputStream);
	}
	
	public void askPlayerName() throws IOException{
		out.writeObject("name");
		out.flush();
		try {
			clientName= (String)in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void askPlayerWhatActionToDo() throws IOException {
		out.writeObject("actionToDo");
		out.flush();
		String action="";
		try{
			action=(String)in.readObject();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		controller.PerformAction(this, action);
	}

	@Override
	public void configGame() {
		// TODO Auto-generated method stub
		controller.configGame(this);
	}

	@Override
	public String getName() {
		return clientName;
	}

	@Override
	public void askMaxNumberOfPlayers(Integer maxNumberOfPlayers) throws IOException {
		int maxNumber=10;
		try {
			maxNumber = (Integer)in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller.setMaxNumberOfPlayers(maxNumber);
	}

	@Override
	public void askWichMapToUse(List<String> maps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
