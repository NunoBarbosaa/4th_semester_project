US5001 - Input communication module of the 'AGV Digital Twin'
=======================================


# 1. Requirements

This functionality will allow the AGV Digital Twin to 
* Accept tasks to collect products
* Report task completion

# 2. Analysis

![SVG](US5001%20-%20DM.svg)

# 3. Design

* The following diagram shows how messages and replies are handled which is very similar to the approach taken in the 
AGV Manager. Simply put, when a new connection is received, a new thread is created to handle the communication. When a message
is received, it is parsed and the request is performed, in case the request is unknown, an 'unknown' message is replied, if the
message code is known but the content is not the expected format, an 'Error' message is replied.

![SVG](US5001%20-%20SD0.svg)

## 3.1. Sequence Diagram
In the diagram below, it is shown how the Digital Twin and the AGV Manager interact with each others.
A new Digital Twin Thread is created when a connection request is received from the AGV Manager, and it will handle
the communication until a disconnect message is received.

![SVG](US5001%20-%20SD.svg)

## 3.2. Class Diagram

![SVG](US5001%20-%20CD.svg)

* Above is the class diagram that represents generally, the classes needed for the server to work. As it can be seen,
  every message received in the server is parsed by the method 'parseRequest' in the class 'AgvDigitalTwinProtocolParser', and
  the method always return a reply that is an object that has 'ProtocolV1Request' as his superclass. The latter, has an execute
  method which contains the actions performed by the server when a specific request is received, and it returns a response message.
* Also, worth noting is that for every new TCP connection received in the server, a new thread is created to continue the
  communication without blocking the server and eventually other messages that could come from other clients.



## 3.4. Tests 

* Unit tests will be used to verify that each request is parsed correctly

        @Test
        public void testCommTestIsParsedCorrectly(){
              ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,0,0,0");

              assertEquals(request.execute(), "1,2,0,0");
        }

* unknown messages are identified and return correct reply code

        @Test
        public void testUnknownRequestIsParsedCorrectly(){
              ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,20,0,0");

              assertEquals(request.execute(), "1,11,0,0");
        }

* Errors in known messages are identified and reply is done appropriately

        @Test
        public void testErrorMessagesAreRepliedWhenNeeded1(){
  
              ProtocolV1Request request = DigitalTwinProtocolParser.parseRequest("1,0,0,0,2");
              assertEquals(request.execute(), "1,10,46,0,\"Number of parameters different than expected\"");

              ProtocolV1Request request1 = DigitalTwinProtocolParser.parseRequest("1,0,1,0");
              assertEquals(request1.execute(), "1,10,33,0,\"Message should have data size 0\"");
        }

* Similar tests will be performed to the other messages


# 4. Implementation

The functionality was implemented on a new module 'agv'. The main class is initialized, and it waits from a new connection to the socket on a specific port. From there, a new
thread is created and initialized to continue the communication with the AGV Manager. It is then expected that the AGV Manager will send a request to assign an AGV that will
prepare a specific order. When the AGV starts the order preparation, the AGV Manager is responsible to update the order status to 'In Preparation'. When the process is
finished, the AGV Digital Twin sends a status update request, to change the order to 'Ready to be packed'. Finally, the AGV Manager sends a disconnect request, the
socket is closed and the thread ends.

# 5. Integration/Demonstration

This functionality works along with the functionality of the US5002, since when the AGV 'leaves the dock' to start an order preparation, it has to send a request to change its
status to 'Occupied' and after the order preparation is done the AGV has to change is status back to 'Available'. This functionality is also what allows the US2003 and US4002 to fully
work properly has it is the one that prepares orders a thing that is needed in both User Stories mentioned.
Finally, the US2004 has a dependency with this one, because that functionality needs this one to have orders being 'ready to be packed'.

# 6. Observations

As of now, this functionality allows the AGV Digital Twins to received orders to prepare. However, currently, the AGV waits 1min to simulate the order being prepared. In the next
iterations it is expected that the AGV will virtually follow a route to pick up each product in the order and the return to his dock. So this is an essential step towards the next iterations
of the product.


