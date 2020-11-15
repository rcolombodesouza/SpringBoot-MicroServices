FROM openjdk:latest
LABEL version="1.0"
LABEL description="This is the authentication service"
LABEL maintainer="rcolombodesouza@gmail.com"
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY target/authentication.jar $PROJECT_HOME/authentication.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/opt/app/authentication.jar"]