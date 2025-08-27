# portfolio-service

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)


## General info
This service collects all the client data by making the user answer a series of questions and calculating the Product A, B and C according to the user assessment and comparing it with portfolios.

## Technologies
Spring Boot : 2.3.1
MySQL Workbench 8.0 CE
JDK : 1.8
Log4j : 2.17.2


#Swagger Path :

https://finlabs-esg-booster.synechron.net/portfolio-mgmt/swagger-ui.html


#Context Path :

/portfolio-mgmt

 

## Setup
 
1. cd <path to portfolio service>/portfolio-service/Dockerfile

2. docker build command

docker build -t portfolio-service:esg .

3. docker run
docker run -itd -p 8081:8081 --name portfolio -e activatedProfile=dev portfolio-service:esg &

##API Documentation

/portfolio-service/src/main/resources/api-docs