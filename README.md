# Recipe Management API
The web application allows users to manage your favorite recipes.

### Required tools to run the API
- JDK 8
- Maven
- Eclipse or IntelliJ Idea
- Lombok plugin for IDE
- Docker Desktop (optional, you can run on your PC)
- PostgreSQL (optional, you can use H2 DB)

## 1- Running on your PC
API config file includes two DB configurations: PostgreSQL and H2 in-memory DB. The project uses H2 DB as default. If you have PostgreSQL on your PC, you can change the configuration. You don’t need to do anything if you want to run with H2 DB. The configuration file name is application.yml under the resources directory.

- H2 DB URL: http://localhost:8080/api/recipeManagement/v1/h2-console
- User Name: admin
- Password: admin
- JDBC URL: jdbc:h2:mem:testdb

![H2 Login Console](https://github.com/yunussezgin/github-project-images/blob/master/recipe-service/h2-console.JPG)

## Run the below commands at the root of the project directory. You can also do the below on your IDE.
Build Recipe API using maven.
```
mvn clean install
```
Run spring boot application.
```
mvn spring-boot:run
```

## 2- Running on Docker
API includes docker config files which are docker file and docker-compose file. Docker creates two containers. The first one is for spring application, and the second is for PostgreSQL DB. Docker uses the application-docker.yml file under the resources directory for application config.

## Run the below commands at the root of the project directory.
Build Recipe API using maven.
```
mvn clean install
```
Create containerized images for the application using a docker-compose command.
```
docker-compose build
```
Run all containers using the single command as below.
```
docker-compose up
```
*In addition, **to stop all containers**, you can use the following command.*
```
docker-compose down
```

