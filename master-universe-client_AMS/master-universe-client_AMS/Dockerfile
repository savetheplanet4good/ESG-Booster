FROM openjdk:11
RUN apt-get update
#RUN apt install curl
#RUN apt-get update
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.12.0-amd64.deb
RUN dpkg -i filebeat-7.12.0-amd64.deb
COPY filebeat.yml /etc/filebeat/filebeat.yml
ARG JAR_FILE=target/MasterUniverse-0.0.1-SNAPSHOT.jar
ADD $JAR_FILE master-universe-service.jar
COPY start.sh start.sh
RUN chmod +x start.sh
EXPOSE 8092
ENTRYPOINT ["./start.sh"]
#ENTRYPOINT ["java","-Dspring.profiles.active=${activatedProfile}","-jar","/master-universe-service.jar"]