#!/bin/sh

if [ -z "$JAVA_HOME" ]; then
   echo -------------------------------------------------------------
   echo WARN: JAVA_HOME environment variable is not set. 
   echo -------------------------------------------------------------
else
   JAVA_CMD=java
   JCACHE_HOME=$(cd `dirname $0`/..; pwd)
   echo set JREKVS_HOME = $JREKVS_HOME
   nohup  $JAVA_CMD -server -XX:+AggressiveOpts -DJREKVS_HOME=$JREKVS_HOME -XX:MaxDirectMemorySize=4G -cp ".:../config/*:../lib/*" io.jrekvs.net.JrekvsMain "$@" > jcache.log 2>&1 &
fi
