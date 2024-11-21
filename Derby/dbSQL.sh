#!/bin/sh

#     *************************************************
#     this script starts the Derby DB

. ./dbSetenv.sh

echo $CLASSPATH
echo $DB_NAME

java -Dij.driver=org.apache.derby.jdbc.ClientDriver -Dij.database=$DB_NAME -Dij.user=guest -Dij.password=password org.apache.derby.tools.ij
