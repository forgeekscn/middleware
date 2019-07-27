FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD fastdfs-service-1.0-SNAPSHOT.jar fastdfs-service-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/fastdfs-service-1.0-SNAPSHOT.jar"]