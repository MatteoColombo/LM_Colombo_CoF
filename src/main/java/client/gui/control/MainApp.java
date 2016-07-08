package client.gui.control;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.control.Controller;
import client.control.RMIServerManager;
import client.control.ServerManager;
import client.control.SocketServerManager;
import client.control.ViewInterface;
import client.gui.model.GameProperty;
import client.gui.view.*;
import client.gui.view.RoomController;
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
import server.model.player.Player;
/**
 * this is the entry point for the graphical user interface
 * @author gianpaolobranca
 *
 */
public class MainApp extends Application implements ViewInterface, Runnable, Controller {

	private static final Logger log= Logger.getLogger( MainApp.class.getName() );
	
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
			log.log( Level.SEVERE, e.toString(), e );
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
			log.log( Level.SEVERE, e.toString(), e );
		}
	}

	public void showConfigGame() {

		try {
			Configuration clientConfig = new Configuration();
			List<String> maps = clientConfig.getMaps();
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
			log.log( Level.SEVERE, e.toString(), e );
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
			log.log( Level.SEVERE, e.toString(), e );
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
			log.log( Level.SEVERE, e.toString(), e );
		}
	}
	
	@Override
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

            MarketController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAll(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
		} catch (IOException e) {
			log.log( Level.SEVERE, e.toString(), e );
		}
	}

	@Override
	public void showClassification(List<Player> players) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/Results.fxml"));
			BorderPane results = (BorderPane) loader.load();
			// Create the dialog Stage.
            Stage stage = new Stage();
            stage.setTitle("FINAL RESULTS");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(results);
            stage.setScene(scene);
            ResultsController controller = loader.getController();
            controller.setResults(players);

            // Show the dialog and wait until the user closes it
            stage.showAndWait();
		} catch (IOException e) {
			log.log( Level.SEVERE, e.toString(), e );
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
			log.log( Level.SEVERE, e.toString(), e );
		}
	}

	public void initSocketManager() {
		try {
			localGame.setConfiguration(new Configuration());
			SocketServerManager socketManager = new SocketServerManager(
					new Socket(localGame.getConfiguration().getServerAddress(),
							localGame.getConfiguration().getSocketPort()),
					this);
			manager = socketManager;
			socketManager.start();
		} catch (IOException | ConfigurationErrorException e) {
			log.log( Level.SEVERE, e.toString(), e );
		}
	}

	public void initRMIManager() {
		try {
			localGame.setConfiguration(new Configuration());
			ServerInt server = (ServerInt) LocateRegistry.getRegistry(localGame.getConfiguration().getServerAddress(),
					localGame.getConfiguration().getRmiPort()).lookup("ServerInt");
			RMIServerManager rmiManager = new RMIServerManager(this);
			manager = rmiManager;
			new RMIUtilityClass(rmiManager, server).start();
		} catch (RemoteException | NotBoundException | ConfigurationErrorException e) {
			log.log( Level.SEVERE, e.toString(), e );
		}
	}

	private class RMIUtilityClass extends Thread {
		RMIServerManager manager;
		ServerInt server;

		public RMIUtilityClass(RMIServerManager manager, ServerInt server) {
			this.server = server;
			this.manager = manager;
		}
		
		@Override
		public void run() {
			try {
				server.login(manager);
			} catch (RemoteException e) {
				log.log( Level.SEVERE, e.toString(), e );
			}
		}
	}

	/**
	 * This method isn't used because with javaFx we don't need to implement it!
	 */
	@Override
	public void printAskWhichMapToUse() {
		// not used
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
	public void printAskConfigurationMethod() {
		showConfigGame();
	}
	
	@Override
	public void printAskPlayersNumber(int max) {
		//not used
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

	@Override
	public void disconnected() {
		//Nothing to do with the GUI
	}


}
