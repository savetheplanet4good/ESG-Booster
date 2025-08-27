FROM openjdk:11
ARG JAR_FILE=target/discovery-server-0.0.1-SNAPSHOT.jar
ADD $JAR_FILE app.jar
EXPOSE 9200
ENTRYPOINT ["java","-jar","/app.jar"]
