package enums;

public enum DeliveryStatus {
	APPROVED {
		public String toString() {
			return "APPROVED";
		}
	},

	NOTAPPROVED {
		public String toString() {
			return "NOTAPPROVED";
		}
	},
	ARRIVED{
		public String toString() {
			return "ARRIVED";
		}
	},
	DONE {
		public String toString() {
			return "DONE";
		}
	}
}
