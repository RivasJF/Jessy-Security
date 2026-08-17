FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar solo el pom.xml primero para aprovechar la cache de capas de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Usuario no root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar JessySecurity-0.1.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "JessySecurity-0.1.jar"]