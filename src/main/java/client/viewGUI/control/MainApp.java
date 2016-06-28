package client.viewGUI.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;

import client.viewGUI.model.GameProperty;
import client.viewGUI.view.ConfigGameController;
import client.viewGUI.view.GameController;
import client.viewGUI.view.LoginController;
import client.viewGUI.view.MarketController;
import client.viewGUI.view.RoomController;
import client.control.Controller;
import client.control.RMIServerManager;
import client.control.ServerManager;
import client.control.SocketServerManager;
import client.control.ViewInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import server.control.connection.ServerInt;
import server.control.dialogue.Dialogue;
import server.control.dialogue.notify.Notify;
import server.control.dialogue.request.Request;
import server.control.dialogue.update.Update;
import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;

public class MainApp extends Application implements ViewInterface, Runnable, Controller {

	private static final long serialVersionUID = 8173423470055513329L;
	private String myName;
	private GameProperty localGame = new GameProperty();
	private Stage primaryStage;
	private ServerManager manager;
	
	private GameController gameController;

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
		if (dialog instanceof Update)
			Platform.runLater(() -> ((Update) dialog).execute(localGame));
		if (dialog instanceof Notify)
			Platform.runLater(() -> ((Notify) dialog).execute(this));
		if (dialog instanceof Request)
			Platform.runLater(() -> ((Request) dialog).execute(this));
	}

	public void showWaitingRoom() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/Room.fxml"));
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
			loader.setLocation(MainApp.class.getResource("/fxml/Login.fxml"));
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

	public void showConfigGame() {

		try {
			// TODO this is just a workaround that need to be fixed
			Configuration clientConfig = new Configuration();
			List<String> maps = clientConfig.getMaps();
			// TODO end workaround
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/ConfigGame.fxml"));
			AnchorPane configGame = (AnchorPane) loader.load();
			Scene scene = new Scene(configGame);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Give the controller access to the main app.
			ConfigGameController config = loader.getController();
			config.setMainApp(this);
			config.setMapList(maps);

		} catch (IOException | ConfigurationErrorException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showRoom() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/Room.fxml"));
			AnchorPane game = (AnchorPane) loader.load();
			Scene scene = new Scene(game);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Give the controller access to the main app.
			RoomController roomController = loader.getController();
			roomController.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showGame() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/Game.fxml"));
			BorderPane game = (BorderPane) loader.load();
			Scene scene = new Scene(game);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Give the controller access to the main app.
			gameController = loader.getController();
			gameController.setAll(this);
			localGame.yourTurnEnded();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMarket() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/Market.fxml"));
			BorderPane market = (BorderPane) loader.load();
			// Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sell");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(market);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            MarketController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAll(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Window getPrimaryStage() {
		return this.primaryStage;
	}

	public void setName(String name) {
		this.myName = name;
	}

	public void sendMsg(String msg) {
		try {
			manager.publishMessage(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initSocketManager() {
		try {
			localGame.setConfiguration(new Configuration());
			SocketServerManager manager = new SocketServerManager(
					new Socket(localGame.getConfiguration().getServerAddress(),
							localGame.getConfiguration().getSocketPort()),
					this);
			this.manager = manager;
			manager.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initRMIManager() {
		try {
			localGame.setConfiguration(new Configuration());
			ServerInt server = (ServerInt) LocateRegistry.getRegistry(localGame.getConfiguration().getServerAddress(),
					localGame.getConfiguration().getRmiPort()).lookup("ServerInt");
			RMIServerManager manager = new RMIServerManager(this);
			this.manager = manager;
			new RMIUtilityClass(manager, server).start();
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class RMIUtilityClass extends Thread {
		RMIServerManager manager;
		ServerInt server;

		public RMIUtilityClass(RMIServerManager manager, ServerInt server) {
			this.server = server;
			this.manager = manager;
		}

		public void run() {
			try {
				server.login(manager);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method isn't used because with javaFx we don't need to implement it!
	 */
	@Override
	public void printAskWhichMapToUse() {
	}

	@Override
	public void printIllegalAction(Exception e) {
		gameController.showAlert(e.getMessage());
	}

	@Override
	public void printAskPlayerName() {
		sendMsg(myName);
	}

	public GameProperty getLocalModel() {
		return localGame;
	}

	@Override
	public void printMessage(String message) {
		gameController.logMsg(message);
	}

	@Override
	public void printAskPlayersNumber(int max) {
		showConfigGame();
	}

	@Override
	public void printAskWhatActionToDo() {
		gameController.changeStatus("");
	}

	@Override
	public void changeStatusToNobilityBonus(String message, String status) {
		gameController.logMsg(message + "\n" + status);
		gameController.changeStatus(status);
	}

}
