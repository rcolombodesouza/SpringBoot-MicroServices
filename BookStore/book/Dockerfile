FROM openjdk:latest
LABEL version="1.0"
LABEL description="This is the application service"
LABEL maintainer="rcolombodesouza@gmail.com"
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY target/book.jar $PROJECT_HOME/book.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/opt/app/book.jar"]