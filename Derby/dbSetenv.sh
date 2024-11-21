#!/bin/sh

#     *************************************************
#     this script sets up the environment for Derby


# This environment variable names the databse
export DB_NAME=jdbc:derby://localhost:1527/JavaTunesDB

# This environment variable points to the Derby install directory
export DERBY_INSTALL=../DerbyLib

# We use the following in dbStart.cmd to deal with Java 7 security issues
export DERBY_PORT=1527

export CLASSPATH=.:$DERBY_INSTALL/derbytools.jar:$DERBY_INSTALL/derbynet.jar:$DERBY_INSTALL/derbyclient.jar:$DERBY_INSTALL/derby.jar:$CLASSPATH

echo $CLASSPATH
