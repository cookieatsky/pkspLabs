@rem     ************************************************
@rem     this script starts the Derby database server
@rem     ************************************************

@rem     change the following line to 'echo on' for debugging
@echo off

setlocal

@rem     call dbSetenv.cmd script to set up the Derby environment
call dbSetenv

@rem     start the Derby database server - We have to run with no security manager installed by Derby
@rem     because the default security policy won't let the server run.
java org.apache.derby.drda.NetworkServerControl start -noSecurityManager

endlocal