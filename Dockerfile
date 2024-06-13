FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/spring-boot-app-tienda-0.0.1.jar
COPY ${JAR_FILE} app_tienda.jar
EXPOSE 8080
ENTRYPOINT [ "java" ,"-jar","app_tienda.jar" ]