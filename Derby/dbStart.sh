#!/bin/sh

#     *************************************************
#     this script starts the Derby DB

. ./dbSetenv.sh

#  start the Derby database server - We have to run with no security manager installed by Derby
#  because the default security policy won't let the server run.
java org.apache.derby.drda.NetworkServerControl start -noSecurityManager

