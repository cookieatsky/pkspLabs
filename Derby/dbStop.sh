#!/bin/sh

#     *************************************************
#     this script stops the Derby DB

. ./dbSetenv.sh

java org.apache.derby.drda.NetworkServerControl shutdown
