US4001 - Create the 'AGV Manager' component
=======================================


# 1. Requirements

* This is a server application that will support both backoffice application and also the AGV digital twin.
* Communications with and from the AGV Manager must follow the SPOMSP communication protocol.
* The AGV Manager will send/assign tasks to the AGVs according to their availability and also taking their specifications into consideration. 
This will be done via the "CMD API" which communicates with AGVs Digital Twins.
* This component will receive data from each AGV via the "Status API" regarding their status. 


# 2. Design

New communication messages codes will be added to the SPOMS Protocol. 
An important **note** is that, in the data section, strings of characters must be surrounded by quotes and numbers like integers and doubles should not have them.


| Code | Meaning                                                                                                                                                                                                                                      |
|:----:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|  3   | **TASKASSIGN** - Message sent from the Backoffice to the AGV Manager. It needs to have data in the following format:<br/>OrderId;AGV ID (i.e. 1,"AGV2").<br/> An ACK message is returned to the backoffice app if the request was successful |
|  4   | **AVAILAGV** - Message sent from the AGV Manager to the Backoffice. In the data section it has the AGV ID, the Description and the current location (i.e. "AGV2","agv description",12,13)                                                    |
|  5   | **ASSIGNAGV** - Message sent from the AGV Manager to the AGV Digital Twin. The data section only contains the order ID and the AGV id (i.e. 1,"AGV2")                                                                                        |
|  6   | **STATUSUPD** - Message sent from the AGV Digital Twin to the AGV Manager. It updates the status of the AGV. The data section of the message contains the AGV ID and the new status (i.e. AGV2,"Charging")                                   |
|  7   | **ORDERUPD** - Message sent from the AGV Digital Twin to the AGV Manager. It is sent when the AGV finishes picking up an order. The data only contains the order id followed by the new order status                                         |
|  8   | **ASKAGVLIST** - Message sent from the Backoffice to the AGV Manager. When this message is received, the AGV Manager replies with a message containing all AGVs available. This message does not contain data.                               |
|  9   | **AVAILAGVLIST** - Message sent from the AGV Manager to the Backoffice. In the data section of this message, there is a header with each AGV attribute that will be sent and the a list of AGVs and their attributes                         |
|  10  | **ERRORMSG** - Message used when a request received wasn't possible to perform. This message has no data.                                                                                                                                    |
|  11  | **UNKNOWNREQUEST** - Message used when a request received isn't known. This message has no data                                                                                                                                              |

The design of this server application is divided in several sections according to features that are needed to support other applications needs.

The following sequence diagram shows how a normal communication between the server and the client happens. It always starts with a 
'commTest' by the client and an acknowledgment reply by the server. Likewise, it always ends with a disconnect request by the client and an acknowledgment
by the server. In between these messages, the communication happens. Any message sent to the server has a reply, however if the message is not known, an
'unknown request' is replied. If the message code is known but the data doesn't follow the expected format and/or content, an 'error in request' is replied.

![SVG](US4001%20-%20SD0.svg)


## 2.1 US2003 Server support

### 2.1.1. Sequence Diagram

This sequence diagram supports the US2003 functionality on the server side. As can be seen, the AGV Manager 
receives a message from the backoffice application, as the message has code 3, this means that a task will be assigned 
to an agv. Both order ID and AGV ID are included in the message data section. The AGV Manager app proceeds to 
communicate with the AGV digital twin and tells it to prepare the order.

![SVG](US4001%20-%20SD1.svg)

## 2.2 Class Diagram

 * Below is the class diagram that represents generally, the classes needed for the server to work. As it can be seen,
every message received in the server is parsed by the method 'parseRequest' in the class 'AgvManagerProtocolParser', and
the method always return a reply that is an object that has 'ProtocolV1Request' as his superclass. The latter, has an execute
method which contains the actions performed by the server when a specific request is received, and it returns a response message.
 * Also, worth noting is that for every new TCP connection received in the server, a new thread is created to continue the 
communication without blocking the server and eventually other messages that could come from other clients.

![SVG](US4001%20-%20CD.svg)

## 2.3. Tests

* To verify that requests are handled correctly and that there are no regressions in future revisions of the code don't regress
the following unit tests will be created

        @Test
        public void testCommTestIsParsedCorrectly(){
              ProtocolV1Request request = AgvManagerProtocolParser.parseRequest("1,0,0,0");

              assertEquals(request.execute(), "1,2,0,0");
        }

* The following test is to ensure that an unknown request generates a reply to the client with code 11. 

        @Test
        public void testUnknownRequestIsParsedCorrectly(){
              ProtocolV1Request request = AgvManagerProtocolParser.parseRequest("1,20,0,0");

              assertEquals(request.execute(), "1,11,0,0");
        }

* The test below is to ensure that when a known message with content/formatting different from the one expected returns an
error in request with the appropriate message.

        @Test
        public void testErrorMessagesAreRepliedWhenNeeded1(){
  
              ProtocolV1Request request = AgvManagerProtocolParser.parseRequest("1,0,0,0,2");
              assertEquals(request.execute(), "1,10,46,0,\"Number of parameters different than expected\"");

              ProtocolV1Request request1 = AgvManagerProtocolParser.parseRequest("1,0,1,0");
              assertEquals(request1.execute(), "1,10,33,0,\"Message should have data size 0\"");
        }

* Similar tests to the ones above will be done to the other requests that are handled/known by the AGV Manager Protocol Parser.


# 3. Implementation

This functionality is implemented in a new module/application named 'AgvManager'. Nonetheless, it has classes in the core to maximize code 
re-utilization has some classes such as the 'ProtocolV1Request' are also used in the 'AGV Digital Twin' application. As seen in the subcategory above
about unit tests, reply messages and the parsing of incoming messages is thoroughly tested to ensure that an error message or unknown message is always 
replied when necessary. 

# 4. Integration/Demonstration

This application is the base support for multiple functionalities related to order preparation and AGVs. Namely, both US2003 & 4002 which use the AGV Manager to
assign an AGV to new orders needing preparation, the first one being executed manually by a warehouse employee and the other is a functionality automatically performed
by the AGV Manager.

# 5. Observations

There is still work to do on the AGV Manager to enhance its functionalities like securing TCP connections, but in its current status it is already 
an important piece of the whole ecosystem and performs the necessary work at the time of writing.



