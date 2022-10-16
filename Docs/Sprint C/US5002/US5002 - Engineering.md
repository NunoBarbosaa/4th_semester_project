US5002 - Output communication module of the 'AGV Digital Twin'
=======================================


# 1. Requirements

The AGV Digital Twin needs to update its status on the AGV Manager. that is:
* After taking an order, the AGV changes its status to 'Occupied'
* After finishing preparing an order, the AGV changes its status to 'Available'
* Eventually, when the draining of the battery is calculated, the AGV will change its status to 'Charging' when the battery is below 10% or something similar

# 2. Analysis

When the US2002 (AGV configuration) was developed, it was defined that an AGV could have one of four different status
at one point in time, these were 'Available', 'Occupied', 'Charging' and 'In Maintenance'. In this User Story, the Status_API
will be developed which will allow the AGV to change its status. In this current stage of development of the project. The AGV 
will only use its communication module to change its status from 'Available' to 'Occupied' and vice-versa.

The following diagram was extracted from the domain model.

![SVG](US5002%20-%20DM.svg)

# 3. Design

* This functionality is designed to follow the same communication architecture as the one used in the Input Communication Module of the AGV and also on the AGV Manager.

![SVG](US5002%20-%20SD0.svg)

## 3.1. Sequence Diagram

In the diagram below, it is shown how the Digital Twin and the AGV Manager interact with each others.
It starts by sending a CommTest message then a 'STATUSUPD' message is sent, which contains the AGV ID and the new Status.
The communication ends when the AGV sends a Disconnect Request and the AGV Manager replies with an Acknowledgment.

![SVG](US5002%20-%20SD.svg)

## 3.2. Class Diagram

![SVG](US5002%20-%20CD.svg)


# 4. Implementation

The functionality was implemented on the module 'agv' which was first introduced in the US5001. It communicates with the AGV Manager in a different socket as the one used to receive product orders assignments, this one is the
Status_API, and it sends 'STATUSUPD' requests to the AGV Manager. The AGV manager receives the request and if the content and format is according to the protocol, that it will change the AGV
status and update it on the database.

# 5. Integration/Demonstration

The functionality developed works in harmony with the US5001 (AGV input communication module). This is because, when an AGV received a request to prepare an order, it will update his
status to 'Occupied'. This step is very important to get right, otherwise, an AGV that is preparing an order could appear as available to the AGV Manager and the later could try and
assign another order to the same AGV which does not make sense. The same goes for when the AGV finishes preparing an order. If, it has enough energy in the battery to perform another
task then it should update his to 'Available'.

# 6. Observations

The feature implemented in this User Story is still in the early stages, and in the upcoming sprint it will be enhanced, so that the AGVs can change their status to 'Charging' when
needed.



