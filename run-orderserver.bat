REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.ordersserver\target\base.app.ordersserver-1.4.0-SNAPSHOT.jar;base.app.ordersserver\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% base.app.ordersserver.OrdersServer
