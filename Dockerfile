FROM openjdk:21
MAINTAINER hk
COPY target/github-proxy-0.0.1-SNAPSHOT.jar github-proxy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/github-proxy-0.0.1-SNAPSHOT.jar"]