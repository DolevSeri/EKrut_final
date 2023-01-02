package clientControllers;

import entities.Device;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController {

	public <T, S> void setColumn(TableColumn<T, S> column, String name) {
		column.setCellValueFactory(new PropertyValueFactory<T, S>(name));

	}

//	public <T, S> void setCell(TableColumn<T, S> column, TableCell<T, S> cellFactory) {
//		column.setCellFactory((param) -> cellFactory);
//	}
	public <T, S> void setCellEditable(TableColumn<T, S> column) {
		column.setOnEditCommit(event -> {
			T rowValue = event.getRowValue();
			S newValue = event.getNewValue();
			castObject(rowValue,newValue);	
		});
	}

	private <T, S> void castObject(T rowValue, S newValue) {
		if (rowValue instanceof Device && newValue instanceof Integer) {
			Device device = (Device)rowValue;
			device.setThreshold((int)newValue);
		}
	}
}
