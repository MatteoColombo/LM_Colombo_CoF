package fx.view;

import java.io.IOException;
import java.util.List;

import fx.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ConfigGameController {

	@FXML private TableView<StringProperty> mapTable;
	@FXML private TableColumn<StringProperty, String> mapColumn;
	@FXML private TextField playersNumber;
	
	private ObservableList<StringProperty> mapList;
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setMapList(List<String> maps) {
		mapList = FXCollections.observableArrayList();
		for(String map: maps) {
			mapList.add(new SimpleStringProperty(map));
		}
		mapTable.setItems(mapList);
		
		// TODO add listener for changes in map table selections
	}
	
	@FXML private void initialize() {
		mapColumn.setCellValueFactory(cell -> cell.getValue());
		mapColumn.setCellFactory(cell -> {
			return new TableCell<StringProperty, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if(empty) {
						setText(null);
						setStyle("");
					} else {
					super.updateItem(item, empty);
					setText(item);
					setStyle("-fx-background-color:#f0f8ff");
					}
				}
			};
		}
		);
	}
	
	@FXML
	private void handlePlay() throws IOException {
		mainApp.sendMsg("" + playersNumber.getText() + " " + mapTable.getSelectionModel().getSelectedIndex());
	}
	
	@FXML private void handleAdd() {
		this.mapList.add(0, new SimpleStringProperty("pluto"));
	}

}
