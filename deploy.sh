#!/bin/bash
echo " killing Process" 
#Fetching process ID if exists and killing it. 
ps -ef| grep app.jar| grep -v grep| awk '{print $2}'| xargs kill -9

#Exporting a varibale so that build is not killed and excute command in background to start an application
export BUILD_ID=dontKillMe

echo " Starting app.jar as Java application"

# Starting application in background mode
nohup java -jar ~/app.jar > ~/applogs.log 2>&1 &

echo " Deployment Finished"