# alternative-service

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)


## General info
This service is reponsible to provide the alternatives for companies so that we can improve our portfolio score by replacing the poor performing companies with better recommended alternatives.
These alternatives are fetched from the third-party api i.e. 'Invest-suite'.

## Technologies
Spring Boot : 2.3.1
MySQL Workbench 8.0 CE
JDK : 1.8
Log4j : 2.17.2


#Swagger Path :

https://finlabs-esg-booster.synechron.net/alternative-service/swagger-ui.html


#Context Path :

/alternative-service

 

## Setup

1. cd <path to alternative service>/Alternative/Dockerfile

2. docker build command

docker build -t alternative-service:esg .

3. docker run
docker run -itd -p 8096:8096 --name alternative -e activatedProfile=dev alternative-service:esg & 

##API Documentation

/Alternative/src/main/resources/api-docs