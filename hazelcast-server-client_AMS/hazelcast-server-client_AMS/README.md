# hazelcast-server

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)


## General info
This service acts as the 'Caching' server where cache is stored outside the service instance but accessible to all other services.

## Technologies
Spring Boot : 2.3.1
MySQL Workbench 8.0 CE
JDK : 1.8
Log4j : 2.17.2

 

## Setup

1. cd <path to portfolio service>/hazelcast-server/Dockerfile

2. docker build command

docker build -t hazelcast-server:esg .

3. docker run
docker run -itd -p 8085:8085 --name portfolio -e activatedProfile=prod hazelcast-server:esg &