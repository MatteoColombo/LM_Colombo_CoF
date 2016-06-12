package fx.view;

import java.io.IOException;
import java.util.List;

import fx.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConfigGameController {

	@FXML
	private TableView<StringProperty> mapTable;
	
	@FXML
	private TableColumn<StringProperty, String> mapColumn;
	
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
		mapColumn.setCellValueFactory(cell -> cell.getValue());
		
		// TODO add listener for changes in map table selections
	}
	
	@FXML
	private void handlePlay() throws IOException {
		mainApp.sendMsg("" + mapTable.getSelectionModel().getSelectedIndex());
	}

}
