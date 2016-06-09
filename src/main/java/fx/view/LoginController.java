package fx.view;

import fx.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private TextField nameField;
	
	@FXML
	private RadioButton rmiButton;
	
	@FXML
	private RadioButton socketButton;

	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void HandleConnect() {
		 mainApp.showRoom();
	}
}
