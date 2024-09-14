FROM openjdk:17-slim
WORKDIR /app
COPY target/appointment-management-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
