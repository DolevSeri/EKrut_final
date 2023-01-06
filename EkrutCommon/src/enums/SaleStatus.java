package enums;

public enum SaleStatus {
	ACTIVATE{
		public String toString() {
			return "ACTIVATE";
		}
	},
	DONE{
		public String toString() {
			return "DONE";
		}
	},
	NEEDTOACTIVATE{
		public String toString() {
			return "NEEDTOACTIVATE";
		}
	},
}
