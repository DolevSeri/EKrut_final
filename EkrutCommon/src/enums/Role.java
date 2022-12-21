package enums;

public enum Role {
	CEO{
		public String toString() {
			//the string will be the exact name of the FXML we would like to show
			return "CEO_MainView.fxml";
		}
	},

	
	Costumer{
		public String toString() {
			//the string will be the exact name of the FXML we would like to show
			return "MainOrderFrame.fxml";
		}
	},
	/*
	Marketing_Employee{
		public String toString() {
			//the string will be the exact name of the FXML we would like to show
			return "MarketingEmployeeMenu.fxml";
		}
	},
	
	Store_Manager{
		public String toString() {
			//the string will be the exact name of the FXML we would like to show
			return "StoreManagerMenu.fxml";
		}
	},
	Store_Employee{
		public String toString() {
			//the string will be the exact name of the FXML we would like to show
			return "StoreEmployeeMenu.fxml";
		}
	},
	Delivery_Man{
		public String toString() {
			//the string will be the exact name of the FXML we would like to show
			return "DeliveryManMenu.fxml";
		}
	}*/
}
