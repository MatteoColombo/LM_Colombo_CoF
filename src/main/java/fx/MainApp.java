package fx;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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
import model.Configuration;
import model.board.Region;
import model.exceptions.ConfigurationErrorException;
import model.exceptions.IllegalActionException;
import model.player.Player;
import model.reward.Reward;
import server.ServerInt;
import view.p2pdialogue.Dialogue;
import view.p2pdialogue.notify.Notify;
import view.p2pdialogue.request.Request;
import view.p2pdialogue.update.Update;

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
		endOfTurn(); // set binded disabled action button parameters to true
						// (disable when is not your turn)
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/Game.fxml"));
			BorderPane game = (BorderPane) loader.load();
			Scene scene = new Scene(game);
			primaryStage.setScene(scene);
			primaryStage.show();
			// Give the controller access to the main app.
			GameController gameController = loader.getController();
			gameController.setAll(this);
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

	@Override
	public void printAskPlayersNumber(int max) {
		showConfigGame();
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
	public void printAskWhichMapToUse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printAskWhatActionToDo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printCities() {
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
		return localGame;
	}

	public void endOfTurn() {
		localGame.getMyPlayerData().canNotDoMainAction().set(true);
		localGame.getMyPlayerData().canNotDoSideAction().set(true);
	}

	@Override
	public void playerJoined(Player p) {
	}

	@Override
	public void setAllPlayers(List<Player> players) {
	}

	@Override
	public void updatePlayer(Player p, int index) {
	}

	@Override
	public void isYourTurn() {
		localGame.getMyPlayerData().canNotDoMainAction().set(false);
		localGame.getMyPlayerData().canNotDoSideAction().set(false);
	}

	@Override
	public void setCityRewards(List<Reward> bonusList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printIllegalAction(Exception e) {
		// TODO Auto-generated method stub

	}
}
