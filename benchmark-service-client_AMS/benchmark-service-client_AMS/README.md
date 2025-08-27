# benchmark-service

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)


## General info
This service is responsible to provide all the benchmark apis. Benchmark is calculated at the data engineering side. Relative calculation for portfolio with each benchmark is done to represent how portfolio is performing against each Benchmark Index in E,S,G and ESG.

## Technologies
Spring Boot : 2.3.1
MySQL Workbench 8.0 CE
JDK : 1.8
Log4j : 2.17.2


#Swagger Path :

https://finlabs-esg-booster.synechron.net/benchmark/swagger-ui.html


#Context Path :

/benchmark

 

## Setup

1. cd <path to benchmark service>/benchmark-service/Dockerfile
2. docker build command

docker build -t benchmark-service:esg .

3. docker run
docker run -itd -p 8095:8095 --name benchmark -e activatedProfile=dev benchmark-service:esg & 

##API Documentation

/benchmark-service/src/main/resources/api-docs