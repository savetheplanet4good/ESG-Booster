# master-universe

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)


## General info
This service acts as the 'Master Data' service which includes all the operations performed on the data and is also responsible for fetching the master-universe company list from datalake service.

## Technologies
Spring Boot : 2.3.1
MySQL Workbench 8.0 CE
JDK : 1.8
Log4j : 2.17.2


#Swagger Path :

https://finlabs-esg-booster.synechron.net/master-universe/swagger-ui.html


#Context Path :

/master-universe

 

## Setup

1. cd <path to portfolio service>/MasterUniverse/Dockerfile

2. docker build command

docker build -t master-universe:esg .

3. docker run
docker run -itd -p 8092:8092 --name portfolio -e activatedProfile=dev master-universe:esg  &
 

##API Documentation

/MasterUniverse/src/main/resources/api-docs