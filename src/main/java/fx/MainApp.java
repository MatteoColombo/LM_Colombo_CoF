package fx;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import client.view.ServerManager;
import client.view.SocketServerManager;
import client.view.ViewInterface;
import fx.view.ConfigGameController;
import fx.view.LoginController;
import client.control.Controller;
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
import view.p2pdialogue.Dialogue;

public class MainApp extends Application implements ViewInterface, Runnable, Controller {

	private String myName;
	
	private Stage primaryStage;
    private ServerManager manager;
	private boolean canWrite = false;	
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
		this.canWrite = true;
	}

	
	public void showRoom() {
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
			SocketServerManager temp;
			temp = new SocketServerManager(new Socket("localhost", 1994), this);
			this.manager = temp;
			temp.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void printAskPlayersNumber(int max) {
		// FIXME this test-only implementation
		try {
			sendMsg("4");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showInitMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showGetConnectionType() {
		// TODO Auto-generated method stub
		
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
}
