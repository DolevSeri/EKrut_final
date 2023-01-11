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

	Get_User_Data {
		public String toString() {
			return "Request to pull user data";
		}
	},

	User_Data_Imported {
		public String toString() {
			return "User data imported successfully";
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
	import_Sales {
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
			return "Sales  succsesfully saved!";
		}
	},

	System_msg_updated {
		public String toString() {
			return "System msg updated succssesfully!";
		}
	},
	Save_TakeAway {
		public String toString() {
			return "Request to save Takeaway order";
		}

	},
	TakeAway_Saved {
		public String toString() {
			return "Takeaway order saved succssesfully!";
		}
	},
	Get_PickUp_Orders {
		public String toString() {
			return "Request to get Takeaway orders";
		}
	},
	PickUp_Orders_imported {
		public String toString() {
			return "Takeaway order imported succssesfully!";
		}
	},
	Update_PickUp_Status {
		public String toString() {
			return "Request to update Takeaway order as collected";
		}
	},
	Updated_PickUp_Status {
		public String toString() {
			return "Updated  PickUp order succssesfully!";
		}
	},
	Save_New_Delivery {
		public String toString() {
			return "Request to save delivery order in DB";
		}
	},
	Delivery_Saved {
		public String toString() {
			return "Updated  Delivery order succssesfully!";
		}
	},
	Create_Customer_Request{
		public String toString() {
			return "Request to create new custumer";
		}
	},
	Update_sale_status{
		public String toString() {
			return "Request to update sale status";
		}
	},
	Updated_sale_status{
		public String toString() {
			return "Updated  sale status succssesfully!";
		}
	},
	Import_orderbyname{
		public String toString() {
			return "request to import  order ";
		}
	},
	Imported_orderbyname{
		public String toString() {
			return "import order succssesfully!";
		}
	},
	Customer_Created{
		public String toString() {
			return "Customer created successfully";
		}
	},
	Update_Customer_Request{
		public String toString() {
			return "Request to update customer to member";
		}
	},
	Customer_Updated{
		public String toString() {
			return "Customer updated to member successfully";
		}
	},
	Get_Customer_Data{
		public String toString() {
			return "Request to import customer data";
		}
	}, 
	Customer_Data_Imported{
		public String toString() {
			return "Customer data imported successfully";
		}
	},
	Update_SaleStatusdone{
		public String toString() {
			return "Request to update sale data";
		}
	}, 
	SaleStatus_Updateddone{
		public String toString() {
			return "sale status updated successfully";
		}
	},
	Get_Deliveries_By_Area{
		public String toString() {
			return "Request to import deliveries of area";
		}
	},
	Area_Deliveries_Imported{
		public String toString() {
			return "Area deliveries imported successfully";
		}
	},
	Get_Deliveries_ToApprove_By_Area{
		public String toString() {
			return "Request to import deliveries to approve of area";
		}
	},
	Area_Deliveries_ToApprove_Imported{
		public String toString() {
			return "Area deliveries to approve imported successfully";
		}
	},
	Change_Delivery_Status{
		public String toString() {
			return "Request to change deliveries to approve";
		}
	},
	Delivery_Status_Changed{
		public String toString() {
			return "Deliveries status changed successfully";
		}
	}
}
