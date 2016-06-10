package fx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.Client;
import fx.view.LoginController;
import fx.view.RoomController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.board.Region;
import view.client.ViewInterface;

public class MainApp extends Application implements ViewInterface, Runnable {

	private Stage primaryStage;
    private List<String> clientData = new ArrayList<String>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Council of Four");
		showLogin();
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

	/*public static void main(String[] args) {
		launch();
	}*/

	public Window getPrimaryStage() {
		return this.primaryStage;
	}
	
	/*public ClientInt getClient() {
		return this.client;
	}*/
	
	public List<String> getTestClientData() {
		return this.clientData;
	}
	
	public void setTestClientData(String c) {
		this.clientData.add(c);
	}

	@Override
	public void printAskPlayersNumber(int max) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printAskWhichMapToUse(List<String> maps) {
		// TODO Auto-generated method stub
		
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
	public void printAskPlayerName() {
		// TODO Auto-generated method stub
		showLogin();
	}

	@Override
	public void printIllegalAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		launch();
	}
}
