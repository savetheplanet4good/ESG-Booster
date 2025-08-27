FROM openjdk:11
ARG JAR_FILE=target/hazelcast-server-0.0.1-SNAPSHOT.jar
ADD $JAR_FILE app.jar
EXPOSE 5701
EXPOSE 5702
EXPOSE 5703
EXPOSE 8085
ENTRYPOINT ["java","-Dspring.profiles.active=${activatedProfile}","-jar","/app.jar"]
