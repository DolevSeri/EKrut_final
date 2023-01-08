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
	DONE {
		public String toString() {
			return "DONE";
		}
	},
}
