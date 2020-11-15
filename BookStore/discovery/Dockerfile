FROM openjdk:latest
LABEL version="1.0"
LABEL description="This is the discovery service"
LABEL maintainer="rcolombodesouza@gmail.com"
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY target/discovery.jar $PROJECT_HOME/discovery.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/opt/app/discovery.jar"]