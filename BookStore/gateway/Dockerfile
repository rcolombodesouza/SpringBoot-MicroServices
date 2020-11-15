FROM openjdk:latest
LABEL version="1.0"
LABEL description="This is the gateway service"
LABEL maintainer="rcolombodesouza@gmail.com"
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY target/gateway.jar $PROJECT_HOME/gateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/gateway.jar"]