RCOMP 2021-2022 Project - Sprint C planning
===========================================
### Sprint master: 1201835 ###

# 1. Sprint's backlog #

* **US-2003** As Warehouse Employee, I want to access the list of orders that need to be prepared 
  by an AGV and be able to ask/force any of those orders to be immediately prepared by an AGV available.

* **US-4001** As Project Manager, I want that the "AGVManager" component supports properly, at request, 
  the needs of the "BackOfficeApp" application as well as the needs the AGV digital twin.

* **US-4002** As Project Manager, I want that the "AGVManager" component is enhanced with a basic 
  FIFO algorithm to automatically assign tasks to AGVs.

* **US-5001** As Project Manager, I want that the team start developing the input communication module 
  of the AGV digital twin to accept requests from the "AGVManager".

* **US-5002** As Project Manager, I want that the team start developing the output communication module
  of the AGV digital twin to update its status on the "AGVManager".
  
* **US-2005** As Warehouse Employee, I want to open a web dashboard presenting the current status of the
  AGVs as well as their position in the warehouse layout and keeps updated automatically (e.g.: at each minute).
  
* **US-1501** As Customer, I want to view/search the product catalog and be able to add a product to the shopping 
  cart.
  
* **US-1901** As Project Manager, I want that the "OrdersServer" component supports properly, at request, the needs
  of the "CustomerApp" application.

# 2. Technical decisions and coordination #


## SPOMS Protocol

The following message codes will be added to the SPOMS Protocol, they will allow communication of request as well as data transfer when needed.
Worth noting that the head of each message will also follow the defined SPOMS Protocol.


| Code | Meaning                                                                                                                                                                                                                                                                    |
|:----:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|  3   | **TASKASSIGN** - Message sent from the Backoffice to the AGV Manager. It needs to have data in the following format:<br/>OrderId;AGV ID (i.e. 1;AGV2).<br/> An ACK message is returned to the backoffice app if the request was successful                                 |
|  4   | **AVAILAGV** - Message sent from the AGV Manager to the Backoffice. In the data section it has the AGV ID, the Description and the current location (i.e. AGV2;agv description;12;13)                                                                                      |
|  5   | **ASSIGNAGV** - Message sent from the AGV Manager to the AGV Digital Twin. The data section only contains the order ID and the AGV id (i.e. 1;AGV2)                                                                                                                        |
|  6   | **STATUSUPD** - Message sent from the AGV Digital Twin to the AGV Manager. It updates the status of the AGV. The data section of the message contains the AGV ID and the new status (i.e. AGV2;Charging)                                                                   |
|  7   | **ORDERUPD** - Message sent from the AGV Digital Twin to the AGV Manager. It is sent when the AGV finishes picking up an order. The data only contains the order id followed by 1 (change order status to being prepared) or 2 (change order status to ready to be packed) |
|  8   | **ASKAGVLIST** - Message sent from the Backoffice to the AGV Manager. When this message is received, the AGV Manager replies with a message containing all AGVs available. This message does not contain data.                                                             |
|  9   | **AVAILAGVLIST** - Message sent from the AGV Manager to the Backoffice. In the data section of this message, there is a list of AGV IDs (i.e. AGV1;AGV3;AGV7).                                                                                                             |
|  10  | **ERRORMSG** - Message used when a request received wasn't possible to perform. This message has no data.                                                                                                                                                                  |
|  11  | **UNKNOWNREQUEST** - Message used when a request received isn't known. This message has no data                                                                                                                                                                            |
|  12  | **CART_ADD** - Message sent from the Customer Application. It needs to have data in the following format:<br/>ProductId;Quantity.<br/> An ACK message is returned to the Customer app if the request was successful. *Adds item to shopping cart*                          |
|  13  | **CART_REMOVE** - Message sent from the Customer Application. It needs to have data in the following format:<br/>ProductId;.<br/> An ACK message is returned to the Customer app if the request was successful. *Removes item from shopping cart*                          |                                              
|  14  | **CUSTOMER_ID** - Message sent from the Customer Application. It needs to have data in the following format:<br/>CustomerEmail;.<br/> An ACK message is returned to the Customer app if the request was successful. *Connects Customer to session*                         |                                                                                    |
|  15  | **CATALOG** -  Message sent from the Customer Application. It needs to have data in the following format:<br/>CatalogFilter;Key.<br/> An ACK message is returned to the Customer app if the request was successful. *Returns filtered product's catalog*                   |                               
|  16  | **CATEGORIES** -  Message sent from the Customer Application. Returns available categories. An ACK message is returned to the Customer app if the request was successful.                                                                                                  |                              
|  18  | **SHOPCHART** - Message sent from the Customer Application. Returns customer's shopping cart. An ACK message is returned to the Customer app if the request was successful.                                                                                                |                
|  21  | **NRSURVEYS** - Message sent from the Customer Application. Returns the number of Surveys that a specific system user has to answer. The message contains the system user.                                                                                                 |
|  22  | **NRSURVEYSREPLY** - Message sent from the Orders Server. Contains the number of surveys that the customer has to answer                                                                                                                                                   |
|  23  | **LISTSURVEYS** - Message sent from the Customer application to the Order Server. Contains the customer username. Usually a reply containing all the surveys is replied                                                                                                    |
|  24  | **LISTSURVEYSREPLY** - Message that typically is a reply to the message code 22. Contains all the surveys that a customer has to answer in the data section                                                                                                                | 
|  25  | **SAVEANSWER** - Message sent from the Customer application to the Order Server. Contains the Answers List in the data section. If the process goes without failures, an acknowledge message is replied.                                                                   |
|  26  | **WAREHOUSEPLANTREQUEST** - Message sent from the digital twin to the AGV manager. Contains no data                                                                                                                                                                        | 
|  27  | **WAREHOUSEPLANTREPLY** -                                                                                                                                                                                                                                                  |
## Servers and Clients

During the sprint, three new Application modules will be created, namely, the 'AGV Manager', the 'AGV Digital Twin' and the 'Orders Server'. The first one is a client application when communicating via the CMD_API 
and a server application when receiving communications on both Request_API and Status_API. The second one is also both server and client, while the last one is only server application.

## Other details

Each feature implemented will have its own documentation were the process and communication sequences will be explained more in-depth. <br>
Below are the links for each one:

[US1501](../../Sprint%20C/US1501/US1501.md)<br>
[US1901](../../Sprint%20C/US1901/US1901.md)<br>
[US2003](../../Sprint%20C/US2003/US2003.md)<br>
[US2005](../../Sprint%20C/US2005/US2005.md)<br>
[US4001](../../Sprint%20C/US4001/US4001%20-%20Engineering.md)<br>
[US4002](../../Sprint%20C/US4002/US4002.md)<br>
[US5001](../../Sprint%20C/US5001/US5001%20-%20Engineering.md)<br>
[US5002](../../Sprint%20C/US5002/US5002%20-%20Engineering.md)<br>



# 3. Subtasks assignment #

  * 1201534 - US-4001, US-5001, US-5002
  * 1201330 - US-1901
  * 1201544 - US-4002, US-1501
  * 1201835 - US-2003, US-2005

