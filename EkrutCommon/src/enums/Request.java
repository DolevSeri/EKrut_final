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
	Logout_request {
		public String toString() {
			return "Request Logout";
		}
	},
	LoggedOut {
		public String toString() {
			return "User Logged out";
		}
	},
	Get_Devices_By_Area {
		public String toString() {
			return "Request devices by area";
		}
	},
	Devices_Imported {
		public String toString() {
			return "Devices imported successfully";
		}
	},
	GetOrdersReportData {
		public String toString() {
			return "Request orders report";
		}

	},
	GetDeliveryReportData {
		public String toString() {
			return "Request delivery report";
		}

	},
	DeliveryReportData_Imported {
		public String toString() {
			return "Delivery report imported successfully";
		}

	},
	OrdersReportData_Imported {
		public String toString() {
			return "Orders data imported successfully";
		}
	},

	GetInventoryReportData {
		public String toString() {
			return "Inventory report data imported successfully";
		}
	},
	InventoryReportData_Imported {
		public String toString() {
			return "Inventory data imported successfully";
		}
	},
	GetCostumersReportData {
		public String toString() {
			return "Costumers report data imported successfully";
		}
	},
	CostumersReportData_Imported {
		public String toString() {
			return "Costumers data imported successfully";
		}
	},
	Threshold_Update_Request {
		public String toString() {
			return "Request update threshold";
		}
	},
	Threshold_Updated {
		public String toString() {
			return "Threshold updated successfully!";
		}
	},
	Get_Products {
		public String toString() {
			return "Request updated Products!";
		}
	},
	Products_Imported {
		public String toString() {
			return "Products updated successfully!";
		}
	},
	Get_Costumer {
		public String toString() {
			return "Request update Costumer";
		}
	},
	Costumer_Imported {
		public String toString() {
			return "Costumer imported succsesfuly!";
		}
	},
	Get_Not_Approved_Costumers_By_Area {
		public String toString() {
			return "Request costumers by area";
		}
	},
	Costumers_Imported {
		public String toString() {
			return "Costumers imported successfully!";
		}
	},
	Costumer_Update_Status_Request {
		public String toString() {
			return "Request update customer status";
		}
	},
	Costumer_Status_Updated {
		public String toString() {
			return "Customer status updated successfully!";
		}
	},
	Send_msg_to_system {
		public String toString() {
			return "System set message for AreaManagers!";
		}
	},
	Create_Inventory_Call {
		public String toString() {
			return "Area Manager request to open inventory call";
		}
	},
	Inventory_Call_Created {
		public String toString() {
			return "Inventory call created successfully";
		}
	},
	getOrders {
		public String toString() {
			return "Request to import orders";
		}
	},
	Orders_imported {
		public String toString() {
			return "Orders imported succsesfuly!";
		}
	},
	SaveOrder {
		public String toString() {
			return "Request to save Order";
		}
	},
	Order_Saved {
		public String toString() {
			return "Orders succsesfuly saved!";
		}
	},
	Update_Products_In_Device {
		public String toString() {
			return "Request to update products in device";
		}
	},
	Products_updated_In_Device {
		public String toString() {
			return "Products succsesfuly updated in device!";
		}
	},
	get_Msg_In_System {
		public String toString() {
			return "Request to import msg from DB";
		}
	},
	imported_Msg_In_System {
		public String toString() {
			return "succsesfuly imported messages! ";
		}
	},
	Get_Inventory_Calls_By_Area {
		public String toString() {
			return "Request inventory calls by area";
		}
	},
	Inventory_Calls_Imported {
		public String toString() {
			return "Inventory calls imported successfully";
		}
	},
	Inventory_Calls_To_Close {
		public String toString() {
			return "Request to close inventory calls";
		}
	},
	Inventory_Calls_Closed {
		public String toString() {
			return "Inventory calls closed successfully";
		}
	},
	Update_SalesPattern {
		public String toString() {
			return "Request to update Sales pattern";
		}
	},
	SalesPattern_Saved {
		public String toString() {
			return "Sales pattern succsesfuly saved!";
		}
	},
	import_SalesPattern {
		public String toString() {
			return "Request to import SalesPattern from DB";
		}
	},
	imported_SalesPattern {
		public String toString() {
			return "succsesfuly imported SalesPattern! ";
		}
	},
	import_Sales{
		public String toString() {
			return "Request to import Sales from DB";
		}
	},
	imported_Sales {
		public String toString() {
			return "succsesfuly imported Sale! ";
		}
	},
	Update_Sales {
		public String toString() {
			return "Request to update Sales";
		}
	},
	Sales_Saved {
		public String toString() {
			return "Sales  succsesfuly saved!";
		}
	},
	
	
}
