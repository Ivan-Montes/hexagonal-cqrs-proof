#!/bin/bash
#set -e 
#set -x

echo "Start Redis script"

echo "Start server in background and wait for 2 sec"
redis-server --daemonize yes && sleep 2 

echo "Dump data"
redis-cli < /usr/local/bin/init-data.redis

echo "Persist"
redis-cli save 

echo "Stop background server"
redis-cli shutdown 

echo "Start the server"
redis-server --save 60 10 --loglevel warning --requirepass 12345

echo "End Redis script"

