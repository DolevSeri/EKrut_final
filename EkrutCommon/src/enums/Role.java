package enums;

public enum Role {
	CEO {
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "CEO_MainView.fxml";
		}
	},

	Costumer {
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "Costumer";
		}
	},
	AreaManager {
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "AreaManager_MainView.fxml";
		}
	},

	Store_Manager {
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "AreaManager_MainView.fxml";
		}
	},
	DeliveryOP {
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "AreaManager_MainView.fxml";
		}
	},
	OpWorker {
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "AreaManager_MainView.fxml";
		}
	},
	NotSignUp{
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "ScreenForNotSignUpUser.fxml";
		}
	},

	UserManagement{
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "UserManagement_MainView.fxml";
		}
	}

	SalesWorker{
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "SalesWorker_MainView.fxml";
		}
	},
	SalesManager{
		public String toString() {
			// the string will be the exact name of the FXML we would like to show
			return "SalesManagerMainScreen.fxml";
		}
	},

}
