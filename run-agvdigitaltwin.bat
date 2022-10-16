REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.agv\target\base.app.agv-1.4.0-SNAPSHOT.jar;base.app.agv\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% base.app.agv.AgvDigitalTwin
