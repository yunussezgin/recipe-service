# Recipe Management API
The web application allows users to manage your favorite recipes.

### Required tools to run the API
- JDK 8
- Maven
- Eclipse or IntelliJ Idea
- Lombok plugin for IDE
- Docker Desktop (optional, you can run on your PC)
- PostgreSQL (optional, you can use H2 DB)

# Running the Recipe API
## 1. Running on your PC
API config file includes two DB configurations: PostgreSQL and H2 in-memory DB. The project uses H2 DB as default. If you have PostgreSQL on your PC, you can change the configuration. You don’t need to do anything if you want to run with H2 DB. The configuration file name is application.yml under the resources directory.

- H2 DB URL: http://localhost:8080/api/recipeManagement/v1/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- User Name: admin
- Password: admin

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

## 2. Running on Docker
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

# Invoke the Recipe API
## 1. Invoke with Swagger UI
Recipe API includes Swagger UI implementation. You can get information about API endpoints and models on Swagger UI. You can invoke the API endpoints with Swagger UI.

- Swagger UI: http://localhost:8080/api/recipeManagement/v1/swagger-ui/
- Username: admin
- Password: admin

![SwaggerUI](https://github.com/yunussezgin/github-project-images/blob/master/recipe-service/swagger-ui-1.JPG)

You can reach the endpoints under the recipe. After choosing an operation, click the Try it out button. Then fill in the request body or parameters. After then click the Execute button. You can see the response in the response body box.

![SwaggerUIRequest](https://github.com/yunussezgin/github-project-images/blob/master/recipe-service/swagger-ui-2.JPG)

![SwaggerUIResponse](https://github.com/yunussezgin/github-project-images/blob/master/recipe-service/swagger-ui-3.JPG)

## 2. Invoke with Postman

The project includes postman collection, which provides sample API requests. The postman collection is under the resources/postman directory. You can import the collection to your postman.
- Postman collection: https://github.com/yunussezgin/recipe-service/blob/dev/src/main/resources/postman/Recipe%20API.postman_collection.json
- Unit test data: https://github.com/yunussezgin/recipe-service/tree/dev/src/test/resources/data

![Postman](https://github.com/yunussezgin/github-project-images/blob/master/recipe-service/Postman-1.JPG)

API supports basic authentication. You can change user name and password on application.yml. Currently defined values the below.
- Username: admin
- Password: admin

![PostmanAuthorization](https://github.com/yunussezgin/github-project-images/blob/master/recipe-service/Postman-2.JPG)

You must send two keys/values with the header. “admin:admin” is equal “YWRtaW46YWRtaW4=” as Base64 encoded.
- Authorization: Basic YWRtaW46YWRtaW4=
- Content-Type: application/json;charset=utf-8

![PostmanHeaders](https://github.com/yunussezgin/github-project-images/blob/master/recipe-service/Postman-3.JPG)
