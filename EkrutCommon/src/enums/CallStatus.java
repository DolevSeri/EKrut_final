package enums;

public enum CallStatus {
	OPEN{
		public String toString() {
			return "Call is open";
		}
	},
	DONE{
		public String toString() {
			return "Call is done";
		}
	},
}
