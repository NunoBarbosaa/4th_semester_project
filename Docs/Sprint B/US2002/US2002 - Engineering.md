US2002 - Configure the AGVs available on the warehouse.
=======================================


# 1. Requirements

* This functionality is to be implemented in the backoffice application, and will be mostly used by warehouse employees.

**Q&A with the client**

> **Question:** Can AGVs share Docks?
>
> **Answer:** Every AGV has a base location, i.e.  an AGV dock. The same AGV dock can not be used by two (or more) distinct AGVs.

> **Question:** What do you understand about configuring the AGV's available on the warehouse. What changes the warehouse employee can do in his specifics and actions/tasks?
>
> **Answer:** Within this context, "configuring the AGVs available on the warehouse" means that the Warehouse Employee needs to specify which are the AGV operating in the warehouse and, therefore, define some basic information regarding each AGV.

> **Question:** What should the default autonomy when creating an AGV be?
> 
> **Answer:** There is no default value. The user must type the AGV autonomy in minutes according to the manufacture specifications.

# 2. Analysis

## 2.1 What is needed to configure an AGV:

  * The following attributes:
    * AGV identifier (not empty alphanumeric code with at 8 chars maximum)
    * short description (not empty having 30 chars maximum)
    * AGV model (not empty having 50 chars maximum)
    * maximum weight
    * max volume that it can carry
    * base location (i.e. AGV dock)
    
  * AGV status can be:
    * Available (i.e. Can be assigned to an order)
    * Occupied (i.e. Is serving a given order)
    * Charging
    * In Maintenance

## 2.2 Constraints

 * In a warehouse, the maximum number of AGVs is defined by the number of AGV docks available in it.

## 2.3 Other observations

 * During the AGV configuration, the employee will be asked to provide the current status of the AGV.
 * The default position of the AGV after it was created is the same as the dock location it was assigned to.

## 2.4 Relevant Domain Model

The following model represents what is needed to accomplish the functionality, in therms on domain concepts.
The warehouse and AGV Dock aggregate only have their roots represented to show their connection to the AGV.

![SVG](US2002%20-%20DM.svg)

As seen in the diagram above, the AGV entity as a connection (One to One), to a Dock. The dock itself is connected to a warehouse.
This allows us to always know where the AGV is supposed to dock, since it will always be on the same dock, but also, when an AGV is being
configured it is possible to know which docks are already occupied and which one are free to be used by the new AGV.

# 3. Design

This functionality will be used by warehouse employees, a UI will be added to the backoffice application.
In the core, a new package called AGV management will be added, and it will contain the controller, domain classes
and persistence. To persist necessary data for this functionality, mainly the AGV configuration, an AGV repository will be needed.

**Note:**
After the AGV is persisted, the AGV dock that was assigned to it needs to be updated, so it will not appear as free, but instead as occupied.

## 3.1. Sequence Diagram

![SVG](US2002%20-%20SD.svg)

## 3.2. Class Diagram

![SVG](US2002%20-%20CD.svg)

## 3.3. Patterns

* As every other functionality, this one will follow the architecture defined for the application.
User Interfaces will be in the backoffice app package, controllers and domain will be on the 
core package. Persistence classes such as the AgvRepository implementations will be on the 
Persistence Package, although the interface will remain in the core.

* The DTO pattern was used to avoid using the AGV entity directly in the User Interface.

## 3.4. Tests 

**Test 1:** Check that it is not possible to create an AGV with an ID made of more than 8 chars

	@Test(expected = IllegalArgumentException.class)
		public void ensureIDwithMoreThan8CharsIsNotAccepted() {
		Agv agv = new Agv("AGV000011", "AGV 11", "AGV-1", 30, 0.5, 2, "l-");
	}
**Note:** Similar tests will be performed to check description and model constraints

**Test 2:** Check that Agv max weight cannot be less than or equal to 0

	@Test(expected = IllegalArgumentException.class)
		public void ensureMaxWeightIsMoreThan0() {
		Agv agv = new Agv("AGV000011", "AGV 11", "AGV-1", 30, 0.5, 2, "l-");
	}

**Test 3:** Check that null attributes are not accepted

	@Test(expected = IllegalArgumentException.class)
		public void ensureNullDescriptionIsNotAccepted() {
		Agv agv = new Agv("AGV000011", null, "AGV-1", 30, 0.5, 2, "l-");
	}

# 4. Implementation

* The implementation follows the design presented above, Unit Tests are also implemented. Due to the existence of
utilitarian classes such as Description and Measurement, some tests are done directly to those classes instead
of being done to the AGV class, this approach has the same effect, even though the tests are more spread out in
different test classes.

# 5. Integration/Demonstration

* This functionality is very dependent of the Warehouse Plant that is uploaded to the system at startup. It allows the user to choose 
a dock knowing it is currently available and also mark the dock chosen as occupied after the configuration is done correctly.


# 6. Observations

* The functionality implemented will later be extremely important when product orders are assigned to an AGV for it
to go and pick up products in the warehouse. As such



