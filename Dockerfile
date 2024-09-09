FROM openjdk:17-slim
WORKDIR /app
COPY target/appointment-management.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
