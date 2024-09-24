FROM maven:3.9.9-amazoncorretto-17 AS build

COPY . /app
WORKDIR /app

RUN mvn clean package -DskipTests

FROM amazoncorretto:17.0.12
ARG JAR_FILE=app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]