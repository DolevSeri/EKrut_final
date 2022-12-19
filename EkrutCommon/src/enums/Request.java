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
	}, Connected{
		public String toString() {
			return "Client Connected";
		}
	},
}
