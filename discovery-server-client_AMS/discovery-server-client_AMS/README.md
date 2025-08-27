# discovery-server

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)


## General info
This service acts as the 'Eureka' server where all other microservices are registered.
It guides to find the route of communication between microservices.

## Technologies
Spring Boot : 2.3.1
MySQL Workbench 8.0 CE
JDK : 1.8
Log4j : 2.17.2


#Eureka console Path :



# Setup
1. cd <path to discovery server>/discovery-server/Dockerfile

2. docker build command

docker build -t discovery-server:esg .

3. docker run
docker run -itd -p 9200:9200 --name discovery -e activatedProfile=prod discovery-server:esg &