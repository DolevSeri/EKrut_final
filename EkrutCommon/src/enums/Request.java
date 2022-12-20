package enums;

public enum Request {

	Connect_request {
		public String toString() {
			return "Request connect";
		}
	},
	Disconnect_request {
		public String toString() {
			return "Request Disconnect";
		}
	},
	Connected {
		public String toString() {
			return "Client Connected";
		}
	},
	Disconnected {

		public String toString() {
			return "Client Connected";
		}

	},
	Login_Request {

		public String toString() {
			return "Client request Login";
		}

	},
	LoggedIn_Succses {

		public String toString() {
			return "log in succsess";
		}

	},
	LoggedIn_UnsuccsesAlreadyLoggedIn {

		public String toString() {
			return "User is already logged in";
		}

	},
	Unsuccsesful_LogIn {
		public String toString() {
			return "Error! Wrong username OR password";
		}
	},
}
