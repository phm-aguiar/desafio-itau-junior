FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/target/petlocation-*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
