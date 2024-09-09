FROM openjdk:17-jre-slim
WORKDIR /app
COPY target/appointment-management-app.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
