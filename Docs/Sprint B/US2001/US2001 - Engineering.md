US2001 - Setting up the warehouse plant by uploading a JSON file. 
=======================================


# 1. Requirements

* This functionality is to be implemented in the backoffice application and will always be be executed at startup 

**Q&A with the client**

> **Question:** Does a warehouse always have the same plant or can it have different plants in the future?
>
> **Answer:** Each warehouse has its own plant and, therefore, plants might vary from one warehouse to another. However, any warehouse plant is described by a JSON file according to the data structure described in section 5.2 of the specifications' document. On US2001, any JSON file meeting such data structure must be supported.  

> **Question:** Regarding the shelves in each row that are part of an aisle, is there a need to differentiate them?
>
> **Answer:** Yes! You need to differentiate the shelves in each row of an aisle. As you have noticed, each row states how many shelves it has. As so, if a row states it has 4 shelves, it means that the row shelves are identified as shelf 1, 2, 3 and 4 from the bottom to the top.

**Remarks:** 
* a pre-defined JSON file will be automatically uploaded (e.g. at application start-up) 
to get a default warehouse plant and avoiding the employee to execute this action. To change the 
warehouse plant, either the file or the location property in the configuration file is changed.


# 2. Analysis

## 2.1 What characterizes a warehouse

  * A warehouse has an ID and a textual description.
  * Dimensions (length and width) are also relevant.
  * A warehouse has a representation in a grid format.
  * Aisles are placed inside the warehouse, and it's were the products are stored.
  * Each aisle is divided in rows and each rows has one or more shelves.
  * Aisle, rows and shelves have an identifier
  * The warehouse also has docks for the AGVs.
  * Both aisle and docks have a starting square, ending square and one representing depth.
  * Important noting that both aisle and docks have an accessibility side.

## 2.2 Relevant Domain Model

The current domain model does not have warehouses modeled because they are transient entities, 
and so they are only information holders for the data available in the json files.


# 3. Design

The functionality will not have user interaction at runtime, because everything is done at startup time.
The warehouse is created once and is always accessible in the app for other functionalities.

## 3.1. Sequence Diagram

![SVG](US2001%20-%20SD.svg)

The BaseApplication call the class WarehouseContainer, that will initiate the process of creating the warehouse
After completion, the warehouse instance created will be available in that class and can be accessed when needed.

## 3.2. Class Diagram

![SVG](US2001%20-%20CD.svg)

## 3.3. Patterns

The singleton pattern is applied in this functionality, because it only makes sense to create
one instance of the warehouse. The created instance can be used by other functionalities as it contains
all the data from the Json file.


## 3.4. Tests 

**Test 1:** Check that it is not possible to create a warehouse with null dimensions

	@Test(expected = IllegalArgumentException.class)
		public void ensureNullDimensionsAreNotAccepted() {
		Warehouse warehouse = new Warehouse(12, "Warehouse1", null, null, square);
	}

**Test 2:** Check that a row has always more than one shelf

	@Test(expected = IllegalArgumentException.class)
		public void ensureRowWithoutShelvesIsNotAccepted() {
		Row row = new Row(1, square1, square2, 0);
	}

**Test 3:** Check that a warehouse doesn't accept null descriptions

	@Test(expected = IllegalArgumentException.class)
		public void ensureNullDescriptionIsNotAccepted() {
		Warehouse warehouse = new Warehouse(1, null, length, width, square);
	}

**Note:** More tests can and will be done to ensure domain integrity such as making sure that a dock or aisle has always a starting, ending and depth square.
But they follow a similar structure as the ones presented above.

# 4. Implementation

## Configuration file
In the app configuration file there is a property (warehouse.jsonFileLocation) that defines the location of the json file that
contains the warehouse data. By default, the file is located in the root of the project.



# 5. Integration/Demonstration

This functionality is a key component that allows others to work, such as the configuration of 
AGVs because they need to be associated with a dock. Also, the creation of products needs the warehouse
to verify if the location of the product that was provided, in fact exists.

# 6. Observations

The functionality was developed keeping in mind that the software will only have one warehouse.
It works without human intervention as long as the JSON file is in the right location. In future releases 
this could be updated to allow for different warehouses and all of them be stored in a database.
And then each workstation could have a different warehouse according to the place it currently is. However,
as mentioned by the client, this is not a priority.



