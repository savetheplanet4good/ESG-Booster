FROM openjdk:11
RUN apt-get update
#RUN apt install curl
#RUN apt-get update
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.12.0-amd64.deb
RUN dpkg -i filebeat-7.12.0-amd64.deb
COPY filebeat.yml /etc/filebeat/filebeat.yml

ARG JAR_FILE=target/benchmark-service-0.0.1-SNAPSHOT.jar
ADD $JAR_FILE benchmark-service.jar
EXPOSE 8095
COPY start.sh start.sh
RUN chmod +x start.sh
ENTRYPOINT ["./start.sh"]
#ENTRYPOINT ["java","-Dspring.profiles.active=${activatedProfile}","-jar","/benchmark-service.jar"]
