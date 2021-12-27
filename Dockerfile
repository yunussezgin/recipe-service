FROM openjdk:8-jre-alpine
ADD target/recipe-api-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker", "-jar", "/app/app.jar"]
EXPOSE 8080