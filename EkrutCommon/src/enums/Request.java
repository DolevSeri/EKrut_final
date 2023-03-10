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
			return "Client Disconnected";
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
	Create_Customer_Request {
		public String toString() {
			return "Request to create new custumer";
		}
	},
	Update_sale_status {
		public String toString() {
			return "Request to update sale status";
		}
	},
	Updated_sale_status {
		public String toString() {
			return "Updated  sale status succssesfully!";
		}
	},
	Import_orderbyname {
		public String toString() {
			return "request to import  order ";
		}
	},
	Imported_orderbyname {
		public String toString() {
			return "import order succssesfully!";
		}
	},
	Customer_Created {
		public String toString() {
			return "Customer created successfully";
		}
	},
	Update_Customer_Request {
		public String toString() {
			return "Request to update customer to member";
		}
	},
	Customer_Updated {
		public String toString() {
			return "Customer updated to member successfully";
		}
	},
	Get_Customer_Data {
		public String toString() {
			return "Request to import customer data";
		}
	},
	Customer_Data_Imported {
		public String toString() {
			return "Customer data imported successfully";
		}
	},
	Update_SaleStatusdone {
		public String toString() {
			return "Request to update sale data";
		}
	},
	SaleStatus_Updateddone {
		public String toString() {
			return "sale status updated successfully";
		}
	},
	Get_Deliveries_By_Area {
		public String toString() {
			return "Request to import deliveries of area";
		}
	},
	Area_Deliveries_Imported {
		public String toString() {
			return "Area deliveries imported successfully";
		}
	},
	Get_Deliveries_ToApprove_By_Area {
		public String toString() {
			return "Request to import deliveries to approve of area";
		}
	},
	Area_Deliveries_ToApprove_Imported {
		public String toString() {
			return "Area deliveries to approve imported successfully";
		}
	},
	Change_Delivery_Status {
		public String toString() {
			return "Request to change deliveries to approve";
		}
	},
	Delivery_Status_Changed {
		public String toString() {
			return "Deliveries status changed successfully";
		}
	},
	Get_Products_under_thres {
		public String toString() {
			return "Request for products under threshold";
		}
	},
	UpdateProductQuantityAndCloseCall {
		public String toString() {
			return "Request to update product quantity and close the call";
		}
	},
	Product_quantity_updated_succesfully_call_closed {
		public String toString() {
			return "Product quantity updated and the call is closed";

		}
	},
	Send_Notification {
		public String toString() {
			return "Request to send message";
		}
	},
	Msg_Notification {
		public String toString() {
			return "message has been sent successfully";
		}
	},
	New_Notification {
		public String toString() {
			return "Recieved new message";
		}
	},
	Get_Customer_Deliveries {
		public String toString() {
			return "Request to import user deliveries";
		}
	},
	Customer_Deliveries_Imported {
		public String toString() {
			return "User deliveries imported";
		}
	},
	Get_Area_manager_UserName {
		public String toString() {
			return "Request to import area manager usrername";
		}
	},
	Area_manager_UserName_Imported {
		public String toString() {
			return "area manager usrername imported";
		}
	},
	Change_Delivery_Arrival {
		public String toString() {
			return "Request to update delivery arrival time";
		}
	},
	Delivery_Arrival_Changed {
		public String toString() {
			return "Delivery arrival time updated";
		}
	},
	Get_Customer_Username {
		public String toString() {
			return "Request to import username by orderID";
		}
	},
	Customer_Username_Imported {
		public String toString() {
			return "Username imported by orderID";
		}
	},
	Update_ThresholdTable {
		public String toString() {
			return "Request to update product in threshold table ";
		}
	},
	ThresholdTable_Updated {
		public String toString() {
			return "products under threshold table is updated! ";
		}
	},

	Get_Arrived_Deliveries {
		public String toString() {
			return "Request to import arrived deliveries";
		}
	},
	Arrived_Deliveries_Imported {
		public String toString() {
			return "Arrived deliveries imported";
		}
	},
	importNeedToActivateSale {
		public String toString() {
			return "Request to import NEEDTOACTIVATE sales";
		}
	},
	NeedToActivateSale_imported {
		public String toString() {
			return " Need to activate sales imported";
		}
	},
}
