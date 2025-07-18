FROM gradle:8.4-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/Mega-task-1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]