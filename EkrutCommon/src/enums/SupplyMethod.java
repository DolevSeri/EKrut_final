package enums;

public enum SupplyMethod {

	PickUp{
		public String toString() {
			return "Pick Up order";
		}
	},
	
	Standart{
		public String toString() {
			return "Standard order";
		}
	},
	
	Delivery{
		public String toString() {
			return "Delivery order";
		}
	},
}
