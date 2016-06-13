package fx.view;

import java.io.IOException;

import fx.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginController {

	@FXML private TextField nameField;

	@FXML private RadioButton rmiButton;
	@FXML private RadioButton socketButton;
	@FXML private ImageView background;

	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void HandleConnect() throws IOException {
		if(nameField.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Name");
			alert.setHeaderText("No name");
			alert.setContentText("Please insert your name");
			alert.show();
		} else if(!(rmiButton.isSelected() || socketButton.isSelected())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Connection Selected");
			alert.setContentText("Please select a connetion");
			alert.show();
		} else {
			mainApp.setName(nameField.getText());
			if(socketButton.isSelected()) {
				mainApp.initSocketManager();
			} else {
				// TODO init rmi manager
			}
		}
	}
}
