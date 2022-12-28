package enums;

public enum ProductStatus {
	AVAILABLE {
		public String toString() {
		
			return "AVAILABLE";
		}
	},
	NOTAVAILABLE {
		public String toString() {
			return "NOT AVAILABLE";
		}
	},
}
