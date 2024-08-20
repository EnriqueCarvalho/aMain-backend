FROM eclipse-temurin:21-jdk-alpine as build-stage
WORKDIR /app/lib
ADD . .
RUN chmod +x mvnw && ./mvnw clean package

FROM eclipse-temurin:21-jre-alpine as run-stage
WORKDIR /app
COPY --from=build-stage /app/backend/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar
EXPOSE 8090
CMD [ "java", "-jar", "/app/demo.jar" ]