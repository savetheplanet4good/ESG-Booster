# config-server

Config Server will have env specific config for all microservices


## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)


## General info
This service acts as the 'Configuration' server where all the property files of all other microservices are maintained.


## Technologies
Spring Boot : 2.3.1
MySQL Workbench 8.0 CE
JDK : 1.8
Log4j : 2.17.2


 

## Setup
 
1.cd /config-server/Dockerfile


2.docker build command


docker build -t config-server:esg .

3.docker run
docker run -itd -p 8888:8888 --name config -e activatedProfile=prod config-server:esg &