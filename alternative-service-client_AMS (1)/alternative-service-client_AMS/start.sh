#!/bin/bash

#service filebeat start
cd /etc/filebeat
filebeat -c /etc/filebeat/filebeat.yml &
cd ..
cd ..
java -Dspring.profiles.active=${activatedProfile} -jar alternative-service.jar