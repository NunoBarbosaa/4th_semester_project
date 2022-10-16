RCOMP 2021-2022 Project - Sprint D planning
===========================================
### Sprint master: 1201330 ###

# 1. Sprint's backlog #

* **US-3501** As Customer, I want to get the list of questionnaires that the system is asking me to answer and be able to answer any of those questionnaires.

* **US-4003** As Project Manager, I want the communications (of the AGVManager) made through the SPOMS2022 protocol to be secured/protected.
* **US-5003** As Project Manager, I want the input communications (of the AGV Digital Twin) made through the SPOMS2022 protocol to be secured/protected.

* **US-5004** As Project Manager, I want the output communications  (of the AGV Digital Twin) made through the SPOMS2022 protocol to be secured/protected.

* **US-2006** As Project Manager, I want the communications made through the SPOMS2022 protocol to be secured/protected.

* **US-1502** As Customer, I want to view/check the status of my open orders.
  
* **US-1902** As Project Manager, I want the communications made through the SPOMS2022 protocol to be secured/protected.
  
### Since the process to implement SSL/TLS with mutual authentication based on public key certificates is the "same" for all servers, all the information below demonstrates the general rule followed by each US, consequently, no additional documentation was needed.
  


# 2. Technical decisions and coordination #


## SPOMS Protocol

The following message codes were added to the table, to satisfy the functionality of the desired user stories for Sprint D.

| Code | Meaning                                                                                                                                                                                                                       |
|:----:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|  19  | **CLIENT_ORDERS** - Message sent from the Customer Application. An ACK message is returned to the Customer app if the request was successful. *Returns customer's avaliable orders.*                                          |
|  20  | **ORDER_DETAILED_INFO** - Message sent from the Customer Application. An ACK message is returned to the Customer app if the request was successful. *Returns order's detailed information, such as the products it contains.* |
|  21  | **NRSURVEYS** - Message sent from the Customer Application. Returns the number of Surveys that a specific system user has to answer. The message contains the system user.                                                    |
|  22  | **NRSURVEYSREPLY** - Message sent from the Orders Server. Contains the number of surveys that the customer has to answer                                                                                                      |
|  23  | **LISTSURVEYS** - Message sent from the Customer application to the Order Server. Contains the customer username. Usually a reply containing all the surveys is replied                                                       |
|  24  | **LISTSURVEYSREPLY** - Message that typically is a reply to the message code 22. Contains all the surveys that a customer has to answer in the data section                                                                   | 
|  25  | **SAVEANSWER** - Message sent from the Customer application to the Order Server. Contains the Answers List in the data section. If the process goes without failures, an acknowledge message is replied.                      |
|  26  | **WAREHOUSEPLANTREQUEST** - Message sent from the digital twin to the AGV manager. Contains no data                                                                                                                           | 
|  27  | **WAREHOUSEPLANTREPLY** - Message replying to the message code 26                                                                                                                                                             | 

The final overall SPOMS Protocol Table is the following:

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
|  19  | **CLIENT_ORDERS** - Message sent from the Customer Application. An ACK message is returned to the Customer app if the request was successful. *Returns customer's avaliable orders.*                                                                                       |
|  20  | **ORDER_DETAILED_INFO** - Message sent from the Customer Application. An ACK message is returned to the Customer app if the request was successful. *Returns order's detailed information, such as the products it contains.*                                              |
|  21  | **NRSURVEYS** - Message sent from the Customer Application. Returns the number of Surveys that a specific system user has to answer. The message contains the system user.                                                                                                 |
|  22  | **NRSURVEYSREPLY** - Message sent from the Orders Server. Contains the number of surveys that the customer has to answer                                                                                                                                                   |
|  23  | **LISTSURVEYS** - Message sent from the Customer application to the Order Server. Contains the customer username. Usually a reply containing all the surveys is replied                                                                                                    |
|  24  | **LISTSURVEYSREPLY** - Message that typically is a reply to the message code 22. Contains all the surveys that a customer has to answer in the data section                                                                                                                | 
|  25  | **SAVEANSWER** - Message sent from the Customer application to the Order Server. Contains the Answers List in the data section. If the process goes without failures, an acknowledge message is replied.                                                                   |
|  26  | **WAREHOUSEPLANTREQUEST** - Message sent from the digital twin to the AGV manager. Contains no data                                                                                                                                                                        | 
|  27  | **WAREHOUSEPLANTREPLY** - Message replying to the message code 26                                                                                                                                                                                                          |


## SSL Mutual Authentication

Using the given guidelines, sockets were exchanged to SSL sockets in all Servers.

    private SSLSocket socket

The group decided that the key store password to be used, would be the same for all servers:

    12345678

In order to generate the server's private key and public key certificate the following command was used:

    openssl   req -nodes   -x509   -keyout   A.key-out   A.pem-days 365   -newkeyrsa:2048

*Where "A" stands for the server's name.*

Moreover, to generate the java key store containing the application's private key and public key certificate, the following line was used:

    keytool -genkey -v -alias B -keyalg RSA -keysize 2048 -validity 365 -keystore B.jks–storepass secret

*Where "B" stands for the application name and "secret" is the key store password mentioned above.*

After generating the .jks file, the team exported it to a PEM format file using the command:

    keytool -exportcert -alias B -keystore B.jks -storepass secret-rfc -file B.pem

Once both "A" and "B" files were created, "A" .pem file must be provided to application B as a
trusted certificate:

    keytool -import -alias A -keystore B.jks -file A.pem -storepass secret

The following lines were used to ensure mutual authentication between applications. (Logically, this applies for both applications communicating)

    System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
    System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

    System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
    System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);


Below, the used certificates' names (B and A, respectively) by each server are shown. (The certifcates labeled as client_* (B) are the ones used as the "TRUSTED_STORE" parameter above, and are to be utilized when requested by the server).

|         Server          |                                                              Used Certificates                                                               |
|:-----------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------:| 
|     Orders Server       |    *[client_customerapp](../../../certs/client_customerapp.pem)<br>*  *[server_orderserver](../../../certs/server_orderserver.pem)<br>*      |
|   AGV Manager Server    |       *[client_backoffice](../../../certs/client_backoffice.pem)<br>*  *[server_agvmanager](../../../certs/server_agvmanager.pem)<br>*       |
| AGV Digital Twin Server | *[client_backoffice](../../../certs/client_backoffice.pem)<br>*  *[agv_digital_twin_server](../../../certs/agv_digital_twin_server.pem)<br>* |
|   HTTPS Server    |       *[client_backoffice](../../../certs/client_backoffice.pem)<br>*  *[server_agvmanager](../../../certs/server_agvmanager.pem)<br>*       |


## Observations 

User story documentation: (Functionalities which did not require SSL)

[US1502](../../Sprint%20D/US1502/US1502.md)<br>
[US3501](../../Sprint%20D/US3501/US3501%20-%20Engineering.md)<br>

# 3. Subtasks assignment #

  * 1201534 - US-3501, US-4003
  * 1201330 - US-1502, US-1902
  * 1201544 - US-5003, US-5004
  * 1201835 - US-2006

