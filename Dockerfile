FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /app

COPY target/medjava-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]