package fx.view;

import fx.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import view.ClientInt;

public class RoomController {
	
	@FXML
	private TableView<ClientInt> clientTable;
	
	@FXML
	private void initialize() {
		
	}

	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	// TODO more stuff
}
