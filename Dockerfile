FROM eclipse-temurin:23 AS builder
LABEL authors="joyie"

WORKDIR /app

COPY . .

RUN ./mvnw install -DskipTests

FROM eclipse-temurin:23

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8080

# api url 
ENV MY_SERVER_URL=

EXPOSE ${PORT}

HEALTHCHECK --interval=60s --start-period=120s \
    CMD curl -s -f http://localhost:${PORT}/health || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar /app/app.jar -Dserver.port=${PORT}