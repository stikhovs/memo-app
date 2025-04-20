# Stage 1 — сборка приложения с Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

# Тут предполагается, что Liquibase и jOOQ уже отработали в CI (на стадии generate)
RUN mvn clean package -DskipTests

# Stage 2 — финальный образ
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
