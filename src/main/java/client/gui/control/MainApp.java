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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import server.control.connection.ServerInt;
import server.control.instruction.Instruction;
import server.control.instruction.notify.Notify;
import server.control.instruction.request.Request;
import server.control.instruction.update.Update;
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
	public void parseDialogue(Instruction dialog) {
		if (dialog instanceof Update)
			Platform.runLater(() -> ((Update) dialog).execute(localGame));
		if (dialog instanceof Notify)
			Platform.runLater(() -> ((Notify) dialog).execute(this));
		if (dialog instanceof Request)
			Platform.runLater(() -> ((Request) dialog).execute(this));
	}

	/**
	 * launch the stage showing the players in the match, waiting for it to begin
	 * 
	 */
	public void showWaitingRoom() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/Room.fxml"));
			AnchorPane room = (AnchorPane) loader.load();
			Scene scene = new Scene(room);
			primaryStage.setScene(scene);
			// Give the controller access to the main app.
			RoomController roomController = loader.getController();
			roomController.setAll(this);
		} catch (IOException e) {
			log.log( Level.SEVERE, e.toString(), e );
		}
	}

	/**
	 * launch the first stage of the application, before connecting
	 */
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

	/**
	 * launch the stage for the gaming setup
	 */
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
			roomController.setAll(this);

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

	/**
	 * @return the primary stage
	 */
	public Window getPrimaryStage() {
		return this.primaryStage;
	}

	/**
	 * set the name of the player
	 */
	public void setName(String name) {
		this.myName = name;
	}

	/**
	 * send a message to the {@link ServerManager}, which will send it to the server
	 * @param msg the message to send
	 */
	public void sendMsg(String msg) {
		try {
			manager.publishMessage(msg);
		} catch (IOException e) {
			log.log( Level.SEVERE, e.toString(), e );
		}
	}

	/**
	 * initialize the ServerManager as a {@link SocketServerManager}
	 */
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

	/**
	 * initialize the ServerManager as a {@link RMIServerManager}
	 */
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

	/**
	 * create a popup when something goes wrong
	 * 
	 * @param msg
	 *            the message to show in the alert popup
	 */
	@Override
	public void printIllegalAction(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("ERROR");
		alert.setHeaderText("Action not available");
		alert.setContentText(e.getMessage());
		alert.show();
	}

	@Override
	public void printAskPlayerName() {
		sendMsg(myName);
	}
	/**
	 * 
	 * @return the local model for the game
	 */
	public GameProperty getLocalModel() {
		return localGame;
	}

	@Override
	public void printMessage(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("INFO");
		alert.setHeaderText("");
		alert.setContentText(message);
		alert.show();
	}

	@Override
	public void printAskPlayersNumber(int max) {
		//We don't need this with the GUI
	}

	@Override
	public void printAskWhatActionToDo() {
		gameController.changeStatus("");
	}

	@Override
	public void changeStatusToNobilityBonus(String message, String status) {
		printMessage(message);
		gameController.changeStatus(status);
	}

	@Override
	public void disconnected() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setHeaderText("You got Disconnected");
		alert.show();
	}

	@Override
	public void printAskConfigurationMethod() {
		showConfigGame();
	}
}
