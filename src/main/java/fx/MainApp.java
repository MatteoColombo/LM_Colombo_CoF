package fx;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

import client.view.RMIServerManager;
import client.view.ServerManager;
import client.view.SocketServerManager;
import client.view.ViewInterface;
import fx.view.ConfigGameController;
import fx.view.GameController;
import fx.view.LoginController;
import client.control.Controller;
import client.model.GameProperty;
import client.model.PlayerProperty;
import fx.view.RoomController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.board.Region;
import model.player.Player;
import server.ServerInt;
import view.p2pdialogue.Dialogue;

public class MainApp extends Application implements ViewInterface, Runnable, Controller {

	private String myName;
	private GameProperty localGame = new GameProperty();
	private Stage primaryStage;
    private ServerManager manager;

    
	@Override
	public void run() {
		launch();
	}
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Council of Four");
		showLogin();
	}
	
	@Override
	public void parseDialogue(Dialogue dialog) {
		Platform.runLater(() -> dialog.execute(this));
	}

	
	public void showWaitingRoom() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Room.fxml"));
			AnchorPane room = (AnchorPane) loader.load();
			Scene scene = new Scene(room);
			primaryStage.setScene(scene);
			// Give the controller access to the main app.
			RoomController roomController = loader.getController();
			roomController.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Login.fxml"));
			BorderPane loginOverview = (BorderPane) loader.load();
			Scene scene = new Scene(loginOverview);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Give the controller access to the main app.
			LoginController loginController = loader.getController();
			loginController.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showConfigGame(List<String> maps) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ConfigGame.fxml"));
			AnchorPane configGame = (AnchorPane) loader.load();
			Scene scene = new Scene(configGame);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Give the controller access to the main app.
			ConfigGameController config = loader.getController();
			config.setMainApp(this);
			config.setMapList(maps);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void showRoom() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void showGame() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Game.fxml"));
			BorderPane game = (BorderPane) loader.load();
			Scene scene = new Scene(game);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Give the controller access to the main app.
			GameController gameController = loader.getController();
			gameController.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Window getPrimaryStage() {
		return this.primaryStage;
	}
	
	public void setName(String name) {
		this.myName = name;
	}

	public void sendMsg(String msg) throws IOException {
		manager.publishMessage(msg);
	}
	
	public void initSocketManager() {
		try {
			SocketServerManager manager = new SocketServerManager(new Socket("localhost", 1994), this);
			this.manager = manager;
			manager.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initRMIManager() {
		try {
			ServerInt server = (ServerInt) LocateRegistry.getRegistry(1099).lookup("ServerInt");
			RMIServerManager manager = new RMIServerManager(this);
			this.manager = manager;
			server.login(manager);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void printAskPlayersNumber(int max) {
		// TODO remove
	}

	@Override
	public void showInitMenu() {
		// TODO remove this
	}

	@Override
	public void showGetConnectionType() {
		// TODO remove this
	}

	@Override
	public void printAskWhichMapToUse(List<String> maps) {
		// TODO Auto-generated method stub
		showConfigGame(maps);
	}

	@Override
	public void printAskWhatActionToDo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printCities(List<Region> regions) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void printIllegalAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printAskPlayerName() {
		try {
			sendMsg(myName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public GameProperty getLocalModel() {
		return this.localGame;
	}
}
