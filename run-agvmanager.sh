#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=base.app.agvmanager/target/base.app.agvmanager-1.4.0-SNAPSHOT.jar:base.app.agvmanager/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP base.app.agvmanager.AgvManager
