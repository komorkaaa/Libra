# ===== BUILD STAGE =====
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# копируем КОРЕНЬ проекта
COPY pom.xml .
COPY services ./services

# собираем ТОЛЬКО нужный сервис + его зависимости
RUN mvn -pl services/auth-service -am clean package -DskipTests

# ===== RUN STAGE =====
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/services/auth-service/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
