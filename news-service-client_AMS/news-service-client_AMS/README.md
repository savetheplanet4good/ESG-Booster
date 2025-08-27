# new-service

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)


## General info
This service is responsible to provide all the news related apis like top news-insights, news sentiment scores, news heat-map data, etc.
It communicates with the datalake service to get the actual news data.

## Technologies
Spring Boot : 2.3.1
MySQL Workbench 8.0 CE
JDK : 1.8
Log4j : 2.17.2


#Swagger Path :

https://finlabs-esg-booster.synechron.net/news/swagger-ui.html


#Context Path :

/news

 

## Setup

1. cd <path to new-service>/news-service/Dockerfile

2. docker build command

docker build -t news-service:esg .

3. docker run
docker run -itd -p 8099:8099 --name new -e activatedProfile=prod news-service:esg & 

##API Documentation

/news-service/src/main/resources/api-docs